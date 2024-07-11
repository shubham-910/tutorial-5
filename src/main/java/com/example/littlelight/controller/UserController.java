package com.example.littlelight.controller;

import com.example.littlelight.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
public class UserController {

    private List<User> users = new ArrayList<>();
    private AtomicLong counter = new AtomicLong();

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(new ResponseMessage(true, "Users retrieved", users));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body(new ResponseMessage(false, "User not found", null));
        }
        return ResponseEntity.ok().body(new ResponseMessage(true, "User retrieved", user));
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        // Validate the user input
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(400).body(new SimpleResponseMessage(false, "Email is required"));
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            return ResponseEntity.status(400).body(new SimpleResponseMessage(false, "First name is required"));
        }

        // Check for duplicate email
        boolean emailExists = users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));
        if (emailExists) {
            return ResponseEntity.status(409).body(new SimpleResponseMessage(false, "Email already exists"));
        }

        user.setId(counter.incrementAndGet());
        users.add(user);
        return ResponseEntity.ok().body(new SimpleResponseMessage(true, "User added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = users.stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body(new SimpleResponseMessage(false, "User not found"));
        }
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        return ResponseEntity.ok().body(new SimpleResponseMessage(true, "User updated"));
    }

    private static class ResponseMessage {
        private boolean success;
        private String message;
        private Object users;

        public ResponseMessage(boolean success, String message, Object users) {
            this.success = success;
            this.message = message;
            this.users = users;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public Object getUsers() {
            return users;
        }
    }

    private static class SimpleResponseMessage {
        private boolean success;
        private String message;

        public SimpleResponseMessage(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }
}
