package com.company.bookstore.AuthorRepository;

import com.company.bookstore.AuthorModel.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
