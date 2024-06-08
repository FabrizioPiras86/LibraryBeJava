package com.corso.library.controllers.service;


import com.corso.library.entities.Book;
import com.corso.library.entities.User;
import com.corso.library.repository.BookRepository;
import com.corso.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Book> getAvailableBook() {
        return bookRepository.findByUserIsNull();
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book existingBook = bookOptional.get();
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setYear(updatedBook.getYear());
            existingBook.setUser(updatedBook.getUser());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book getById(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

