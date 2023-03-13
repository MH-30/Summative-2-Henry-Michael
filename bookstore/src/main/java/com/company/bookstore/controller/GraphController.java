package com.company.bookstore.controller;


import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.model.Publisher;
import com.company.bookstore.repository.AuthorRepository;
import com.company.bookstore.repository.BookRepository;
import com.company.bookstore.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class GraphController {
    @Autowired
    AuthorRepository authorRepository;


    @Autowired
    PublisherRepository publisherRepository;


     @Autowired
     BookRepository bookRepository;

    @QueryMapping
    public Optional<Author> findAuthorById(@Argument Integer author_id) {
        return authorRepository.findById(author_id);
        /*
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isPresent()) return author;*/
        //System.out.println("Smile");
        /*List<Author> authors = (List<Author>) authorRepository.findAll();
        if (authors.size() == 0) System.out.println("Yes");
        else System.out.println("No");
        for (Author a : authors) System.out.println(a.getFirstName());*/
        //return null;
    }

    @SchemaMapping
    public Author author(Book book) {
        Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthorId());
        if (optionalAuthor.isPresent()) return optionalAuthor.get();
        return null;
    }

    @SchemaMapping
    public Publisher publisher(Book book) {
        Optional<Publisher> returnVal = publisherRepository.findById(book.getPublisherId());
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @QueryMapping
    public Publisher findPublisherById(@Argument Integer publisher_id) {
        Optional<Publisher> publisher = publisherRepository.findById(publisher_id);
        if (publisher.isPresent()) return publisher.get();
        return null;
    }


    @QueryMapping
    public Book findBookById(@Argument Integer book_id) {
        Optional<Book> book = bookRepository.findById(book_id);
        if (book.isPresent()) return book.get();
        return null;
    }



}
