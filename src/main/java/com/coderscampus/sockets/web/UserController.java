package com.coderscampus.sockets.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.sockets.domain.User;
import com.coderscampus.sockets.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(ModelMap model) {
        model.put("user", new User(null, null, null, null, null, null));
        return "register";
    }

    @GetMapping("/login")
    public String getLoginPage(ModelMap model) {
        model.put("user", new User());
        return "login";
    }

    @GetMapping("/users")
    public String getUsers(ModelMap model) {

        List<User> users = userService.findAll();
        model.put("users", users);
        return "users";
    }

    @GetMapping("/users/{id}")
    public String getOneUser(ModelMap model, @PathVariable Long id) {
        User user = userService.findById(id);
        model.put("user", user);
        return "user";
    }

    @PostMapping()
    public User createUser(User user) {
        return userService.createUser(user);
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @PostMapping("/users/{userId}/delete")
    public String postDeleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }
}
