package com.corso.library.repository;

import com.corso.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByLnameAsc();

}
