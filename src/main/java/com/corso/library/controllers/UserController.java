package com.corso.library.controllers;

import com.corso.library.controllers.service.UserService;
import com.corso.library.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(Model model) {
        List<User> users = userService.getAllUserOrderedByLastName();
        model.addAttribute("users", users);
        return "user/index";
    }


    @GetMapping("/create")
    public String showUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/user/";
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/user/";
    }


    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        if (user.isPresent()) {

            User userObj = user.get();
            if (userObj.getDob() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDob = userObj.getDob().format(formatter);
                model.addAttribute("dob", formattedDob);
            }
            model.addAttribute("user", userObj);
        } else {
            model.addAttribute("error", "User not found");
        }
        return "user/update";
    }



    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User user) {
        user.setId(id);
        userService.updateUser(user);
        return "redirect:/user/";
    }
}
