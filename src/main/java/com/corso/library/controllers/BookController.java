package com.corso.library.controllers;

import com.corso.library.controllers.service.BookService;
import com.corso.library.controllers.service.UserService;
import com.corso.library.entities.Book;
import com.corso.library.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model) {
        List<Book> books = bookService.getBooks();
        model.addAttribute("books", books);
        return "book/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        List<User> users = userService.getAllUserOrderedByLastName();
        model.addAttribute("users", users);
        return "book/create"; // Template name
    }

    @PostMapping("/create")
    public String insert(@ModelAttribute Book book, Model model) {
        if (book.getUser() != null && book.getUser().getId() != null) {
            Optional<User> customerOpt = userService.findUserById(book.getUser().getId());
            customerOpt.ifPresent(book::setUser);
        }
        bookService.save(book);
        List<Book> books = bookService.getBooks(); //
        model.addAttribute("books", books); //
        return "redirect:/book/"; //
    }

    @GetMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        if (book != null) {
            model.addAttribute("book", book);
            List<User> users = userService.getAllUserOrderedByLastName();
            model.addAttribute("users", users);
            return "book/update";
        } else {
            return "book/not_found";
        }
    }


    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book updatedBook, @RequestParam(required = false) boolean removeCustomer) {
        Book existingBook = bookService.getById(id);
        if (existingBook != null) {
            if (removeCustomer) {
                existingBook.setUser(null);
            } else {
                if (updatedBook.getUser() != null && updatedBook.getUser().getId() != null) {
                    Optional<User> customerOpt = userService.findUserById(updatedBook.getUser().getId());
                    customerOpt.ifPresent(existingBook::setUser);
                }
            }
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setYear(updatedBook.getYear());
            bookService.save(existingBook);
            return "redirect:/book/";
        } else {
            return "book_not_found";
        }

    }
    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/book/";
    }

}

