package se.iths.martin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class SetupDatabase {

    @Bean
    CommandLineRunner initDatabase(PersonsRepository repository) {
        return args -> {
            if( repository.count() == 0) {
                //New empty database, add some persons
                log.info("Added to database " + repository.save(new Person(0L, "Martin")));
                log.info("Added to database " + repository.save(new Person(0L, "Kalle")));
            }
        };
    }

    @Bean
    RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    Person person1(){
//        return new Person(0L,"Kalle");
//    }
//
//    @Bean
//    Person person2(){
//        return new Person(0L,"Martin");
//    }

}
