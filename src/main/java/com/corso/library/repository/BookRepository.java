package com.corso.library.repository;

import com.corso.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserIsNull();
    List<Book> findAll();


}
