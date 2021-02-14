package com.ecommerce.app.controller;

import com.ecommerce.app.model.UserModel;
import com.ecommerce.app.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  Pankaj Tirpude [tirpudepankaj@gmail.com]
 */


@RestController
@RequestMapping("/ecommerce-app/user")
public class UserController {


    @Resource
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserModel userModel) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.create(userModel));
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> fetchAll() {
       return  ResponseEntity.ok(userService.fetchAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> fetch(@PathVariable("userId") String userId)
    {
       return ResponseEntity.ok(userService.fetch(userId));
    }


}
