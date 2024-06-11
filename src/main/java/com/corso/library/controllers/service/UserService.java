package com.corso.library.controllers.service;


import com.corso.library.entities.User;
import com.corso.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<User> getAllUserOrderedByLastName() {
        return userRepository.findAllByOrderByLnameAsc();
    }


    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }


    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
