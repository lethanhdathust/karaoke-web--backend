package com.programming.karaoke.controller;


import com.programming.karaoke.model.user.User;
import com.programming.karaoke.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping
    public User modifyUser(@RequestBody User user) {
        return userService.updateUser(user);
    }


}
