package se.iths.martin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

public interface PersonsRepository extends JpaRepository<Person, Long> {


    //Custom repository methods
    Person findByName(String name);
//    List<Person> findByIdGreaterThan(Long id);
//    List<Person> findAllByNameIsContaining(String name);
//
//    @Query("Select p from Person p where lower(p.name) like lower(?1)")
//    List<Person> findByNameCustom(String name);
}
