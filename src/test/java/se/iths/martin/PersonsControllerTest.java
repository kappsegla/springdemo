package se.iths.martin;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//https://stackabuse.com/spring-annotations-testing/
//This is a form of Unit testing for spring?
@WebMvcTest(PersonsController.class)
public class PersonsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonsRepository repository;

    @BeforeEach
    void setUp() {
        when(repository.findAll()).thenReturn(List.of(new Person(1L, "Martin"), new Person(2L, "Kalle")));
        when(repository.findById(1L)).thenReturn(Optional.of(new Person(1L, "Martin")));
    }

    @Test
    void getAllReturnsListOfAllPersons() throws Exception {
        //Arrange
        //Create a stub for one of our repository functions
        //       when(repository.findAll()).thenReturn(List.of(new Person(1L,"Martin"),new Person(2L,"Kalle")));

        //Act
        mockMvc.perform(
                get("/api/persons").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\"=1,\"name\"=\"Martin\"},{\"id\"=2,\"name\"=\"Kalle\"}]"));
        //Assert
        //Use Spy functionality in Mock object to verify that findAll was called on repository.
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Calls Get method with url /api/persons/1")
    void getOnePersonWithValidIdOne() throws Exception {
        mockMvc.perform(
                get("/api/persons/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\"=1,\"name\"=\"Martin\"}"));
    }

    @Test
    @DisplayName("Calls Get method with invalid id url /api/persons/3")
    void getOnePersonWithInValidIdThree() throws Exception {
        mockMvc.perform(
                get("/api/persons/3").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}