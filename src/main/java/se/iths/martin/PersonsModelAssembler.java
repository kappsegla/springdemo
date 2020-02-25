package se.iths.martin;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
class PersonsModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    //http://stateless.co/hal_specification.html

    @Override
    public EntityModel<Person> toModel(Person person) {
        return new EntityModel<>(person,
                linkTo(methodOn(PersonsController.class).one(person.getId())).withSelfRel(),
                linkTo(methodOn(PersonsController.class).all()).withRel("persons"));
    }

    @Override
    public CollectionModel<EntityModel<Person>> toCollectionModel(Iterable<? extends Person> entities) {
        var collection = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(collection,
                linkTo(methodOn(PersonsController.class).all()).withSelfRel());
    }
}