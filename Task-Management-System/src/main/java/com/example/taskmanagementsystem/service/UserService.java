package com.example.taskmanagementsystem.service;

import com.example.taskmanagementsystem.enums.Role;
import com.example.taskmanagementsystem.repository.UserRepository;
import com.example.taskmanagementsystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(Long id, String name, Role role){
        User user=getUserById(id);
        user.setName(name);
        user.setRole(role);
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        User user=getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}
