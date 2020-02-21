package se.iths.martin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//Integration test

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
class RegisterUseCaseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonsRepository personsRepository;

    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        Person person = new Person(0L, "Kalle");

        mockMvc.perform(post("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"name\":\"Kalle\"}"));

        //Check with the repository for update
        Person p = personsRepository.findByName("Kalle");
        //Junit assert style
        assertEquals("Kalle", p.getName());

        //AssertJ fluent assert style
        assertThat(p.getName()).startsWith("K").hasSize(5).contains("ll");

        //Hamcrest style
        org.hamcrest.MatcherAssert.assertThat(p.getName(), equalTo("Kalle"));


    }

}