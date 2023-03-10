package com.company.bookstore.repository;



import com.company.bookstore.model.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryTest {
/*
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
*/
    @Autowired
    private AuthorRepository authorRepository;

    @Before
    public void setUp() throws Exception {
        authorRepository.deleteAll();
    }

    @Test
    public void testCreateAuthor() throws Exception {
        // Create the author object
        Author author = new Author();
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("WN");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        // Convert author object into Json
        author = authorRepository.save(author);
        Optional<Author> authorOptional = authorRepository.findById(author.getAuthorId());
        assertEquals(authorOptional.get(), author);

    }

    @Test
    public void testGetAuthorById() throws Exception {
        // Create the author object
        Author author = new Author();
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("WN");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        author = authorRepository.save(author);
        Optional<Author> authorOptional = authorRepository.findById(author.getAuthorId());
        assertEquals(authorOptional.get(), author);

    }

    @Test
    public void testGetAllAuthors() throws Exception {
        // Create 3 authors
        Author author1 = new Author();
        author1.setFirstName("Jonathan");
        author1.setLastName("Scott");
        author1.setStreet("North Palace Ave");
        author1.setCity("Beverly Hills");
        author1.setState("WN");
        author1.setPostalCode("12345");
        author1.setPhone("123-456-7890");
        author1.setEmail("jscott449@gmail.com");

        Author author2 = new Author();
        author2.setFirstName("Daniel");
        author2.setLastName("Scott");
        author2.setStreet("North Palace Ave");
        author2.setCity("Beverly Hills");
        author2.setState("WN");
        author2.setPostalCode("12345");
        author2.setPhone("987-654-3210");
        author2.setEmail("dscott449@gmail.com");

        Author author3 = new Author();
        author3.setFirstName("Brandon");
        author3.setLastName("Scott");
        author3.setStreet("North Palace Ave");
        author3.setCity("Beverly Hills");
        author3.setState("WN");
        author3.setPostalCode("12345");
        author3.setPhone("111-111-1111");
        author3.setEmail("bscott449@gmail.com");

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        List<Author> listOfAuthors = authorRepository.findAll();
        assertEquals(listOfAuthors.size(), 3);
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Author author = new Author();
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("WN");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        authorRepository.save(author);

        author.setStreet("South Palace Ave");
        author.setCity("Beverly City");
        author.setState("WY");
        author.setPostalCode("54321");
        author.setPhone("987-654-3210");

        authorRepository.save(author);

        Optional<Author> authorOptional = authorRepository.findById(author.getAuthorId());
        assertEquals(authorOptional.get(), author);

    }

    @Test
    public void testDeleteAuthor() throws Exception {
        Author author = new Author();
        author.setFirstName("Jonathan");
        author.setLastName("Scott");
        author.setStreet("North Palace Ave");
        author.setCity("Beverly Hills");
        author.setState("WN");
        author.setPostalCode("12345");
        author.setPhone("123-456-7890");
        author.setEmail("jscott449@gmail.com");

        authorRepository.save(author);
        authorRepository.deleteById(author.getAuthorId());
        Optional<Author> authorOptional = authorRepository.findById(author.getAuthorId());
        assertTrue(!authorOptional.isPresent());

    }

}