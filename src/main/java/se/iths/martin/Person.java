package se.iths.martin;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Person {
    @Id @GeneratedValue Long id;
    String name;

    public Person(Long id, String name){
        this.id = id;
        this.name = name;
    }

}
