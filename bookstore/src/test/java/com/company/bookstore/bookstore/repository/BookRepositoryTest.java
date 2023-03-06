package com.company.bookstore.repository;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql({"/schema.sql", "/data.sql"})
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void shouldCreateBook() {
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
        book.setAuthor(author);
        book.setPublisher(publisher);

        // when
        Book savedBook = bookRepository.save(book);

        // then
        assertThat(savedBook.getBookId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        assertThat(savedBook.getAuthor().getFirstName()).isEqualTo("Test");
        assertThat(savedBook.getPublisher().getName()).isEqualTo("Test Publisher");
    }

    @Test
    void shouldReadBookById() {
        // given
        int bookId = 1;

        // when
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        // then
        assertThat(optionalBook).isPresent();
        Book book = optionalBook.get();
        assertThat(book.getTitle()).isEqualTo("An interesting book");
        assertThat(book.getAuthor().getFirstName()).isEqualTo("Stephen");
        assertThat(book.getPublisher().getName()).isEqualTo("McGraw-Hill");
    }

    @Test
    void shouldReadAllBooks() {
        // when
        List<Book> books = bookRepository.findAll();

        // then
        assertThat(books).hasSize(1);
        Book book = books.get(0);
        assertThat(book.getTitle()).isEqualTo("An interesting book");
        assertThat(book.getAuthor().getFirstName()).isEqualTo("Stephen");
        assertThat(book.getPublisher().getName()).isEqualTo("McGraw-Hill");
    }

// Test for Update Book
    @Test
    public void testUpdateBook() throws Exception {
        Book book = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), authorRepository.getById(1), publisherRepository.getById(1), "An interesting book", new BigDecimal("29.99")));

        mockMvc.perform(MockMvcRequestBuilders
                .put("/books/{id}", book.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"isbn\":\"9783161484100\",\"publishDate\":\"2022-01-01\",\"author\":{\"id\":1},\"publisher\":{\"id\":1},\"title\":\"An interesting book\",\"price\":\"29.99\"}"))
                .andExpect(status().isOk());

        Optional<Book> updatedBook = bookRepository.findById(book.getId());
        assertTrue(updatedBook.isPresent());
        assertEquals(book.getId(), updatedBook.get().getId());
        assertEquals("9783161484100", updatedBook.get().getIsbn());
        assertEquals(LocalDate.of(2022, 1, 1), updatedBook.get().getPublishDate());
        assertEquals("An interesting book", updatedBook.get().getTitle());
        assertEquals(new BigDecimal("29.99"), updatedBook.get().getPrice());
    }

    // Test for Delete Book
    @Test
    public void testDeleteBook() throws Exception {
        Book book = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), authorRepository.getById(1), publisherRepository.getById(1), "An interesting book", new BigDecimal("29.99")));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/books/{id}", book.getId()))
                .andExpect(status().isOk());

        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertFalse(deletedBook.isPresent());
    }

    // Test for Find Books by Author ID
    @Test
    public void testFindBooksByAuthorId() throws Exception {
        Author author = authorRepository.save(new Author("Stephen", "King", "Hollywood", "Los Angeles", "CA", "11100", "111-222-3333", "sking@gmail.com"));
        Book book1 = bookRepository.save(new Book("9783161484100", LocalDate.of(2022, 1, 1), author, publisherRepository.getById(1), "An interesting book", new BigDecimal("29.99")));
        Book book2 = bookRepository.save(new Book("9783161484101", LocalDate.of(2022, 2, 1), author, publisherRepository.getById(1), "Another interesting book", new BigDecimal("39.99")));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/books/search/byauthor?id={id}", author.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(book1.getId().intValue())))
                .andExpect(jsonPath("$[0].title", is("An interesting book")))
                .andExpect(jsonPath("$[1].id", is(book2.getId().intValue())))
                .andExpect(jsonPath("$[1].title", is("Another interesting book")));
    }
}

