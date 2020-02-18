package se.iths.martin;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api/")
public class PersonsController {

    private List<Person> personsList = Collections.synchronizedList(new ArrayList<>());

    AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/persons", method = GET)
    public List<Person> allPersons() {
        return personsList;
    }

    @RequestMapping(value = "/persons", method = POST)
    public ResponseEntity<?> createPerson(@RequestBody Person person){

        person.setId(counter.addAndGet(1));

        personsList.add(person);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/persons/" + person.getId());
        return new ResponseEntity<>(person, headers, HttpStatus.CREATED);
    }

}
