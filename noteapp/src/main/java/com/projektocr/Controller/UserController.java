//package com.projektocr.Controller;
//
//import com.projektocr.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    // Endpoint to create user using setters
//    @PostMapping("/createWithSetters")
//    public String createUserWithSetters(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
//        userService.createUserWithSetters(username, password, role);
//        return "User created successfully using setters!";
//    }
//
//    // Endpoint to create user using the constructor
//    @PostMapping("/createWithConstructor")
//    public String createUserWithConstructor(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
//        userService.createUserWithConstructor(username, password, role);
//        return "User created successfully using constructor!";
//    }
//}
