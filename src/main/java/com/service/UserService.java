package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.User;
import com.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User addUser(User user) {
        user.setRole("ROLE_NORMAL");
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(int id) {
        return userRepository.getById(id);
    }
    
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
    
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }
    
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
