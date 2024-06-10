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
        return "book/create";
    }

    @PostMapping("/create")
    public String insert(@ModelAttribute Book book, Model model) {
        if (book.getLikedUser() != null && !book.getLikedUser().isEmpty()) {
            User user = book.getLikedUser().get(0);
            bookService.save(book);
            bookService.addLikedUserToBook(book.getId(), user.getId());
        } else {
            bookService.save(book);
        }
        return "redirect:/book/";
    }

    @GetMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        if (book != null) {
            model.addAttribute("book", book);
            List<User> users = userService.getAllUserOrderedByLastName();
            model.addAttribute("users", users);

            if (book.getLikedUser() != null && !book.getLikedUser().isEmpty()) {
                User currentUser = book.getLikedUser().get(0);
                model.addAttribute("currentUser", currentUser);
            }

            return "book/update";
        } else {
            return "book/not_found";
        }
    }


    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") Book updatedBook, @RequestParam(required = false) boolean removeUserFlag, Model model) {
        Book existingBook = bookService.getById(id);
        if (existingBook != null) {
            if (removeUserFlag) {
                existingBook.getLikedUser().clear(); // Rimuove l'utente associato al libro
            } else {
                if (updatedBook.getLikedUser() != null && !updatedBook.getLikedUser().isEmpty()) {
                    User user = updatedBook.getLikedUser().get(0);
                    existingBook.getLikedUser().clear();
                    existingBook.getLikedUser().add(user); // Aggiunge l'utente selezionato al libro
                }
            }
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbm(updatedBook.getIsbm());
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

