package com.company.bookstore.AuthorController;

import com.company.bookstore.AuthorModel.Author;
import com.company.bookstore.AuthorRepository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebMvcTest(AuthorController.class)
@Transactional
public class AuthorControllerTest {

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAuthor() throws Exception {
        // Create the author object
        Author author = new Author();
        author.setAuthorId(1);
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("Washington");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        // Convert author object into Json
        String inputJson = objectMapper.writeValueAsString(author);

        // Test to see of author was created
        mockMvc.perform(put("/authors").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAuthorById() throws Exception {
        // Create the author object
        Author author = new Author();
        author.setAuthorId(1);
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("Washington");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        authorRepo.save(author);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testGetAllAuthors() throws Exception {
        // Create 3 authors
        Author author1 = new Author();
        author1.setAuthorId(1);
        author1.setFirstName("Jonathan");
        author1.setLastName("Scott");
        author1.setStreet("North Palace Ave");
        author1.setCity("Beverly Hills");
        author1.setState("Washington");
        author1.setPostalCode("12345");
        author1.setPhone("123-456-7890");
        author1.setEmail("jscott449@gmail.com");

        Author author2 = new Author();
        author2.setAuthorId(2);
        author2.setFirstName("Daniel");
        author2.setLastName("Scott");
        author2.setStreet("North Palace Ave");
        author2.setCity("Beverly Hills");
        author2.setState("Washington");
        author2.setPostalCode("12345");
        author2.setPhone("987-654-3210");
        author2.setEmail("dscott449@gmail.com");

        Author author3 = new Author();
        author3.setAuthorId(2);
        author3.setFirstName("Brandon");
        author3.setLastName("Scott");
        author3.setStreet("North Palace Ave");
        author3.setCity("Beverly Hills");
        author3.setState("Washington");
        author3.setPostalCode("12345");
        author3.setPhone("111-111-1111");
        author3.setEmail("bscott449@gmail.com");

        authorRepo.save(author1);
        authorRepo.save(author2);
        authorRepo.save(author3);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = new Author();
        author.setAuthorId(1);
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("Washington");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        Author updatedAuthor = authorRepo.save(author);

        updatedAuthor.setStreet("South Palace Ave");
        updatedAuthor.setCity("Beverly City");
        updatedAuthor.setState("Wyoming");
        updatedAuthor.setPostalCode("54321");
        updatedAuthor.setPhone("987-654-3210");

        // Converted updatedAuthor to Json
        String inputJson = objectMapper.writeValueAsString(updatedAuthor);
        mockMvc.perform(MockMvcRequestBuilders.post("/authors").content(inputJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author1 = new Author();
        author1.setAuthorId(1);
        author1.setFirstName("Jonathan");
        author1.setLastName("Scott");
        author1.setStreet("North Palace Ave");
        author1.setCity("Beverly Hills");
        author1.setState("Washington");
        author1.setPostalCode("12345");
        author1.setPhone("123-456-7890");
        author1.setEmail("jscott449@gmail.com");

        authorRepo.save(author1);

        mockMvc.perform(
                        delete("/customers/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}