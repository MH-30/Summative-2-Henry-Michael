package com.company.bookstore.repository;

import com.company.bookstore.model.Author;
import com.company.bookstore.model.Publisher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherRepositoryTest {
    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    @Before
    public void setUp() throws Exception {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void testCreatePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        publisher = publisherRepository.save(publisher);
        Optional<Publisher> optionalPublisher = publisherRepository.findById(publisher.getId());
        assertEquals(optionalPublisher.get(), publisher);
    }

    @Test
    public void testGetPublisherById() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);
        Optional<Publisher> optionalPublisher = publisherRepository.findById(savedPublisher.getId());
        assertEquals(optionalPublisher.get(), publisher);
    }

    @Test
    public void testGetAllPublishers() throws Exception {
        Publisher publisher1 = new Publisher("Publisher 1", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher publisher2 = new Publisher("Publisher 2", "456 Main St", "Othertown", "NY", "54321", "111-111-1111", "test2@test.com");
        Publisher publisher3 =  new Publisher("Publisher 3", "789 Main St", "Another Town", "FL", "67890", "222-222-2222", "test3@test.com");
        publisherRepository.save(publisher1);
        publisherRepository.save(publisher2);
        publisherRepository.save(publisher3);
        List<Publisher> publishers = publisherRepository.findAll();
        assertEquals(3, publishers.size());
    }

    @Test
    public void testUpdatePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        savedPublisher.setName("New Name");
        savedPublisher.setStreet("456 Other St");
        savedPublisher.setCity("Othertown");
        savedPublisher.setState("NY");
        savedPublisher.setPostalCode("54321");
        savedPublisher.setPhone("111-111-1111");
        savedPublisher.setEmail("test2@test.com");
        savedPublisher = publisherRepository.save(publisher);
        Optional<Publisher> optionalPublisher = publisherRepository.findById(savedPublisher.getId());
        assertEquals(optionalPublisher.get(), publisher);

    }

    @Test
    public void testDeletePublisher() throws Exception {
        Publisher publisher = new Publisher("Test Publisher", "123 Main St", "Anytown", "CA", "12345", "555-555-5555", "test@test.com");
        Publisher savedPublisher = publisherRepository.save(publisher);

        publisherRepository.deleteById(savedPublisher.getId());

        Optional<Publisher> deletedPublisher = publisherRepository.findById(savedPublisher.getId());
        assertFalse(deletedPublisher.isPresent());
    }







}