package com.example.taskmanagementsystem.controller;

import com.example.taskmanagementsystem.enums.Role;
import com.example.taskmanagementsystem.model.User;
import com.example.taskmanagementsystem.repository.UserRepository;
import com.example.taskmanagementsystem.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository){
        this.userService=userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,@RequestParam String name,@RequestParam Role role){
        return userService.updateUser(id,name,role);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "User deactivated successfully";
    }
}
