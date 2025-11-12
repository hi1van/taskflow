package com.example.taskflow.controller;

import com.example.taskflow.model.User;
import com.example.taskflow.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repo;

    public UserController(UserRepository repo) { this.repo = repo; }

    @PostMapping
    public String addUser(@RequestBody User user) {
        repo.saveUser(user);
        return "User saved!";
    }
}
