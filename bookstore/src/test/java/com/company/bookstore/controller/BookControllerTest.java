package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateBook() throws Exception {
        // given
        Author author = new Author();
        author.setFirstName("Test");
        author.setLastName("Author");
        author.setEmail("test.author@example.com");
        author.setPhone("123-456-7890");
        author.setPostalCode("12345");
        author.setState("CA");
        author.setCity("Los Angeles");
        author.setStreet("123 Main St.");

        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");

        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        //book.setAuthorId(book.getAuthorId());
        //book.setPublisherId(book.getPublisherId());

        // when
        String inputJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(post("/books").content(inputJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReadBookById() throws Exception {
        // given
        int bookId = 1;
        Author author = new Author();
        author.setFirstName("Test");
        author.setLastName("Author");
        author.setEmail("test.author@example.com");
        author.setPhone("123-456-7890");
        author.setPostalCode("12345");
        author.setState("CA");
        author.setCity("Los Angeles");
        author.setStreet("123 Main St.");

        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");

        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setAuthorId(author.getAuthorId());
        book.setPublisherId(publisher.getId());
        book.setId(1);
        bookRepository.save(book);
        // when
        String inputJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/1"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReadAllBooks() throws Exception {
        // when
        List<Book> books = bookRepository.findAll();
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));

        bookRepository.save(book);

        Book book2 = new Book();
        book2.setIsbn("1234567890123");
        book2.setPublishDate(LocalDate.now());
        book2.setTitle("Test Book");
        book2.setPrice(new BigDecimal("24.99"));

        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setIsbn("1234567890123");
        book3.setPublishDate(LocalDate.now());
        book3.setTitle("Test Book");
        book3.setPrice(new BigDecimal("24.99"));

        bookRepository.save(book3);

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateBook() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setId(1);
        bookRepository.save(book);
        book.setTitle("Test 2 Book");
        bookRepository.save(book);
        String inputJson = objectMapper.writeValueAsString(book);
        mockMvc.perform(put("/books/1").content(inputJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteBook() throws Exception {
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setId(1);

        bookRepository.save(book);

        bookRepository.deleteById(1);

        mockMvc.perform(
                        delete("/books/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    public void testFindBooksByAuthorId() throws Exception {
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

        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setAuthorId(author.getAuthorId());

        bookRepository.save(book);

        Book book2 = new Book();
        book2.setIsbn("1234567890123");
        book2.setPublishDate(LocalDate.now());
        book2.setTitle("Test Book");
        book2.setPrice(new BigDecimal("24.99"));
        book2.setAuthorId(author.getAuthorId());

        bookRepository.save(book2);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/search/byauthor/1")
                .contentType(MediaType.APPLICATION_JSON));
    }


}