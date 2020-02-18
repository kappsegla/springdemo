package se.iths.martin;

import lombok.Data;

@Data
public class Person {
    Long id;
    String name;

    public Person(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
