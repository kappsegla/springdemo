package se.iths.martin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/persons")
public class PersonsController {

    //private List<Person> personsList = Collections.synchronizedList(new ArrayList<>());
    //A better choice when reading alot and seldom writing, use CopyOnWriteArrayList
    //private List<Person> personsList = new CopyOnWriteArrayList<>();

  //  AtomicLong counter = new AtomicLong();
    final PersonsRepository repository;

    public PersonsController(PersonsRepository storage) {
        this.repository = storage;
    }

    @GetMapping
    public List<Person> allPersons() {
        return repository.findAll();
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> onePerson(@PathVariable long id) {
        var personOptional = repository.findById(id);

        return personOptional.map(person -> new ResponseEntity<>(person, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        var p = repository.save(person);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/persons/" + p.getId());
        return new ResponseEntity<>(p, headers, HttpStatus.CREATED);
    }
}
