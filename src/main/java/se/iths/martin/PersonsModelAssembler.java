package se.iths.martin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class PersonsModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person person) {
        return new EntityModel<>(person,
                linkTo(methodOn(PersonsController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonsController.class).all()).withRel("persons"));
    }
}