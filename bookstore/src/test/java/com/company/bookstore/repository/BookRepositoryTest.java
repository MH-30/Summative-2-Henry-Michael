package com.company.bookstore.repository;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.Assert.*;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@DataJpaTest
//@Sql({"/schema.sql", "/data.sql"})
@RunWith(SpringRunner.class)
@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;


    @Before
    public void setUp() throws Exception {
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    public void shouldCreateBook() throws Exception{
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
        author = authorRepository.save(author);
        publisher = publisherRepository.save(publisher);
        book.setAuthorId(author.getAuthorId());
        book.setPublisherId(publisher.getId());
        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        /*assertThat(savedBook.getAuthor().getFirstName()).isEqualTo("Test");
        assertThat(savedBook.getPublisher().getName()).isEqualTo("Test Publisher");*/
        Optional<Book> book1 = bookRepository.findById(book.getId());

        //Assert...
        assertEquals(book1.get(), savedBook);
    }

    @Test
    void shouldReadBookById() throws Exception {
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
        author = authorRepository.save(author);
        publisher = publisherRepository.save(publisher);
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setAuthorId(author.getAuthorId());
        book.setPublisherId(publisher.getId());
        book = bookRepository.save(book);
        // when
        Optional<Book> optionalBook = bookRepository.findById(book.getId());

        // then
        assertThat(optionalBook.isPresent());
        assertEquals(optionalBook.get(), book);
        //Book book = optionalBook.get();

    }

    @Test
    void shouldReadAllBooks() throws Exception {
        bookRepository.deleteAll();
        Author author = new Author();
        //author.setAuthorId(1);
        author.setFirstName("Test");
        author.setLastName("Author");
        author.setEmail("test.author@example.com");
        author.setPhone("123-456-7890");
        author.setPostalCode("12345");
        author.setState("CA");
        author.setCity("Los Angeles");
        author.setStreet("123 Main St.");
        author = authorRepository.save(author);

        Publisher publisher = new Publisher();
        //publisher.setId(1);
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");
        publisher = publisherRepository.save(publisher);
        Book book = new Book();
        book.setIsbn("1234567890123");
        book.setPublishDate(LocalDate.now());
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("24.99"));
        book.setAuthorId(author.getAuthorId());
        book.setPublisherId(publisher.getId());
        book = bookRepository.save(book);

        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertThat(books).hasSize(1);
    }

    // Test for Update Book
    @Test
    public void testUpdateBook() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");
        publisher = publisherRepository.save(publisher);
        Author author = authorRepository.save(new Author("Stephen", "King", "Hollywood", "Los Angeles", "CA", "11100", "111-222-3333", "sking@gmail.com"));
        Book book = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), author.getAuthorId(), "An interesting book", publisher.getId(), new BigDecimal("29.99")));
        book.setIsbn("9783161484100");
        book = bookRepository.save(book);
        Optional<Book> updatedBook = bookRepository.findById(book.getId());
        assertTrue(updatedBook.isPresent());
        assertEquals(updatedBook.get(), book);
        assertEquals("9783161484100", updatedBook.get().getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), updatedBook.get().getPublishDate());
        assertEquals("An interesting book", updatedBook.get().getTitle());
        assertEquals(new BigDecimal("29.99"), updatedBook.get().getPrice());
    }

    // Test for Delete Book
    @Test
    public void testDeleteBook() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");
        publisher = publisherRepository.save(publisher);

        Author author = authorRepository.save(new Author("Stephen", "King", "Hollywood", "Los Angeles", "CA", "11100", "111-222-3333", "sking@gmail.com"));
        Book book = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), author.getAuthorId(), "An interesting book", publisher.getId(), new BigDecimal("29.99")));
        bookRepository.deleteById(book.getId());
        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertFalse(deletedBook.isPresent());
    }

    // Test for Find Books by Author ID
    @Test
    public void testFindBooksByAuthorId() throws Exception {
        Publisher publisher = new Publisher();
        publisher.setName("Test Publisher");
        publisher.setEmail("test.publisher@example.com");
        publisher.setPhone("123-456-7890");
        publisher.setPostalCode("12345");
        publisher.setState("CA");
        publisher.setCity("Los Angeles");
        publisher.setStreet("123 Main St.");
        publisher = publisherRepository.save(publisher);
        Author author = authorRepository.save(new Author("Stephen", "King", "Hollywood", "Los Angeles", "CA", "11100", "111-222-3333", "sking@gmail.com"));
        Book book1 = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), author.getAuthorId(), "An interesting book", publisher.getId(), new BigDecimal("29.99")));
        Book book2 = bookRepository.save(new Book("9783161484101", LocalDate.of(2022, 2, 1), author.getAuthorId(), "Another interesting book",publisher.getId(), new BigDecimal("39.99")));

        Optional<Book> optionalBook1 = bookRepository.findById(book1.getId());
        Optional<Book> optionalBook2 = bookRepository.findById(book2.getId());
        assertEquals(optionalBook1.get(), book1);
        assertEquals(optionalBook2.get(), book2);
    }
}