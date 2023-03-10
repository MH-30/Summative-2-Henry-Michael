package com.company.bookstore.controller;

import com.company.bookstore.model.Author;
import com.company.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepo;
    //Create
    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody Author author) {
        return authorRepo.save(author);
    }

    // Read by Id
    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable Integer id) {
        Optional<Author> returnAuthor = authorRepo.findById(id);
        if (returnAuthor.isPresent()) return returnAuthor.get();
        return null;
    }


    // Read All
    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        List<Author> authors = (List<Author>) authorRepo.findAll();
        return authors;
    }

    //Update
    @PutMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAuthor(@PathVariable Integer id, @RequestBody Author author) {
        Optional<Author> authorOptional = authorRepo.findById(id);
        if (!authorOptional.isPresent()) return;
        Author searchedAuthor = authorOptional.get();
        searchedAuthor.setFirstName(author.getFirstName());
        searchedAuthor.setLastName(author.getLastName());
        searchedAuthor.setStreet(author.getStreet());
        searchedAuthor.setCity(author.getCity());
        searchedAuthor.setState(author.getState());
        searchedAuthor.setPostalCode(author.getPostalCode());
        searchedAuthor.setPhone(author.getPhone());
        searchedAuthor.setEmail(author.getEmail());
    }

    //Delete
    @DeleteMapping("/authors/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Integer id) {
        Optional<Author> authorOptional = authorRepo.findById(id);
        if (!authorOptional.isPresent()) return;
        authorRepo.deleteById(id);
    }
}
