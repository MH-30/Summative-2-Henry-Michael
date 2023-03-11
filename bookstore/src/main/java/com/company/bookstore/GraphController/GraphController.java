package com.company.bookstore.GraphController;


import com.company.bookstore.model.Author;
import com.company.bookstore.model.Book;
import com.company.bookstore.repository.AuthorRepository;
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

    /*
    * @Autowired
    * PublisherRepository publisherRepository;*/

    /*
     * @Autowired
     * BookRepository bookRepository;*/

    @QueryMapping
    public Author findAuthorById(@Argument Integer author_id) {
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isPresent()) return author.get();
        //System.out.println("Smile");
        List<Author> authors = (List<Author>) authorRepository.findAll();
        if (authors.size() == 0) System.out.println("Yes");
        else System.out.println("No");
        for (Author a : authors) System.out.println(a.getFirstName());
        return null;
    }

    @SchemaMapping
    public Author author(Book book) {
        Optional<Author> optionalAuthor = authorRepository.findById(book.getId());
        if (optionalAuthor.isPresent()) return optionalAuthor.get();
        return null;
    }
    /*
    * @QueryMapping
    public Publisher findPublisherById(@Argument Integer publisher_id) {
        return publisherRepository.getAuthorById(publisher_id);
    }*/

    /*
    * @QueryMapping
    public Book findBookById(@Argument Integer book_id) {
        return bookRepository.getAuthorById(book_id);
    }*/



}
