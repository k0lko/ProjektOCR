//package com.projektocr.Service;
//
//import com.projektocr.Model.User;
//import com.projektocr.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // Method to create a new user using setters (No-argument constructor)
//    public void createUserWithSetters(String username, String password, String role) {
//        User user = new User();  // Using the no-argument constructor
//        user.setUsername(username);  // Using setter
//        user.setPassword(password);  // Using setter
//        user.setRole(role);  // Using setter
//
//        userRepository.save(user);  // Save user to the database
//    }
//
//    // Method to create a new user using the constructor (Parameterized constructor)
//    public void createUserWithConstructor(String username, String password, String role) {
//        User user = new User(username, password, role);  // Using constructor
//
//        userRepository.save(user);  // Save user to the database
//    }
//
//    // Method to find a user by username
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    // Method to update a user's role
//    public void updateUserRole(String username, String newRole) {
//        Optional<User> userOpt = userRepository.findByUsername(username);
//        if (userOpt.isPresent()) {
//            User user = userOpt.get();
//            user.setRole(newRole);
//            userRepository.save(user);
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//
//    // Method to delete a user by username
//    public void deleteUserByUsername(String username) {
//        Optional<User> userOpt = userRepository.findByUsername(username);
//        if (userOpt.isPresent()) {
//            userRepository.delete(userOpt.get());
//        } else {
//            throw new RuntimeException("User not found");
//        }
//    }
//}