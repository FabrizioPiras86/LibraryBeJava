package com.corso.library.controllers;

import com.corso.library.controllers.service.BookService;
import com.corso.library.controllers.service.UserService;
import com.corso.library.entities.Book;
import com.corso.library.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lend")
public class LendController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showLendForm(Model model) {
        List<Book> books = bookService.getAllBooks();
        List<User> users = userService.getAllUsers();
        model.addAttribute("books", books);
        model.addAttribute("users", users);
        return "lend/create";
    }

    @PostMapping("/create")
    public ResponseEntity<String> createLend(@RequestParam Long bookId, @RequestParam Long userId) {
        bookService.addLikedUserToBook(bookId, userId);
        return ResponseEntity.ok("Lend created successfully");
    }

}
