package se.iths.martin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonsController.class)
public class PersonsControllerTest {

    @MockBean
    PersonsRepository repository;

    @Autowired
    MockMvc mockMvc;


    @Test
    void getAllReturnsListOfAllPersons() throws Exception {
        //Arrange
      // PersonsController controller = new PersonsController(repository);
       //Act
       //List<Person> actual = controller.allPersons();
        mockMvc.perform(
                get("/api/persons").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
       //Assert
//       assertEquals(List.of(),actual,"Not empty list returned");
//       verify(repository, times(1)).findAll();
    }
}
