package com.hms.projectSpringBoot.security.controller;

import com.hms.projectSpringBoot.security.entity.User;
import com.hms.projectSpringBoot.security.service.UserService;
import com.hms.projectSpringBoot.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/saveUser")
//    public ApiResponse saveUser(@RequestPart("user") User user) {
//        return userService.save(user);
//    }
//
//    @PutMapping("/updateUser")
//    public ApiResponse updateUser(@RequestPart("user") User user) {
//        return userService.update(user);
//    }

    @PostMapping("/saveUser")
    public ApiResponse saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/updateUser")
    public ApiResponse updateUser(@RequestBody User user) {
        return userService.update(user);
    }


    @DeleteMapping("/deleteById/{id}")
    public ApiResponse deleteById(@PathVariable Long id) {
        return userService.deleteById(id);
    }

}
