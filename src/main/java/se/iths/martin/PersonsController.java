package se.iths.martin;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/persons")
@Slf4j
public class PersonsController {

    //private List<Person> personsList = Collections.synchronizedList(new ArrayList<>());
    //A better choice when reading alot and seldom writing, use CopyOnWriteArrayList
    //private List<Person> personsList = new CopyOnWriteArrayList<>();
    //  AtomicLong counter = new AtomicLong();
    final PersonsRepository repository;
    private final PersonsModelAssembler assembler;

    // Logger log = LoggerFactory.getLogger(PersonsController.class);

    public PersonsController(PersonsRepository storage, PersonsModelAssembler personsModelAssembler) {
        this.repository = storage;
        this.assembler = personsModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Person>> all() {
        log.debug("All persons called");

        var collection = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(collection,
                linkTo(methodOn(PersonsController.class).all()).withSelfRel());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<Person>> one(@PathVariable long id) {
        var personOptional = repository.findById(id);

        return personOptional.map(person -> new ResponseEntity<>(assembler.toModel(person), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        log.info("POST create Person " + person);
        var p = repository.save(person);
        log.info("Saved to repository " + p);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(PersonsController.class).slash(p.getId()).toUri());
        //headers.add("Location", "/api/persons/" + p.getId());
        return new ResponseEntity<>(p, headers, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePerson(@PathVariable Long id) {
        if (repository.existsById(id)) {
            //log.info("Product deleted");
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    ResponseEntity<Person> replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return repository.findById(id)
                .map(person -> {
                    person.setName(newPerson.getName());
                    repository.save(person);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(linkTo(PersonsController.class).slash(person.getId()).toUri());
                    return new ResponseEntity<>(person, headers, HttpStatus.OK);
                })
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    ResponseEntity<Person> modifyPerson(@RequestBody Person newPerson, @PathVariable Long id) {
        return repository.findById(id)
                .map(person -> {
                    if (newPerson.getName() != null)
                        person.setName(newPerson.getName());

                    repository.save(person);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setLocation(linkTo(PersonsController.class).slash(person.getId()).toUri());
                    return new ResponseEntity<>(person, headers, HttpStatus.OK);
                })
                .orElseGet(() ->
                        new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
