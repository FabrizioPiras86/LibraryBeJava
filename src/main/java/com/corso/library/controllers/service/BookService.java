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
            existingBook.setIsbm(updatedBook.getIsbm());
            existingBook.setLikedUser(updatedBook.getLikedUser());
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

    public void addLikedUserToBook(Long bookId, Long userId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (bookOptional.isPresent() && userOptional.isPresent()) {
            Book book = bookOptional.get();
            User user = userOptional.get();
            book.getLikedUser().add(user);
            bookRepository.save(book);
        }
    }


    public void removeUserFromBook(Long bookId, Long userId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        Optional<User> userOptional = userRepository.findById(userId);
        if (bookOptional.isPresent() && userOptional.isPresent()) {
            Book book = bookOptional.get();
            User user = userOptional.get();
            book.getLikedUser().remove(user);
            bookRepository.save(book);
        }
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }



}

