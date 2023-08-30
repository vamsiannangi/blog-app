package com.blogapplication.controllers;

import com.blogapplication.entity.User;

import com.blogapplication.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping("/showMyLoginPage")
    public String showLogin() {
        return "login-form";
    }

    @GetMapping("/registrationForm")
    public String showRegistrationForm(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "register-form";
    }


    @PostMapping("/registration")
    public String registerNewUser(@ModelAttribute User user, Model model) {
        Optional<User> userOptional = userService.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            System.out.println(userService.findByEmail(user.getEmail()));
//        System.out.println("hello");
        model.addAttribute("error", "This email is already exists");
        model.addAttribute("user", user);
        return "register-form";

}
        user.setPassword("{noop}"+user.getPassword());
        user.setRole("ROLE_author");

        userService.save(user);
        return "redirect:/";
    }


    @GetMapping("/access-denied")
    public String showAcessDenied(){
        return "access-denied";
    }

}
