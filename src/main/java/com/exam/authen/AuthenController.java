package com.exam.authen;

import com.exam.authen.user.User;
import com.exam.authen.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenController{

    @Autowired
    UserService userService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<User> authUser(@RequestBody User user){
        User targetUser = userService.getUserByUsernameAndPassowrd(user.getUsername(), user.getPassword());
        String token = tokenService.createToken(targetUser);
        user.setToken(token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<String> check(@RequestHeader("Authorization") String auth){
            tokenService.validateToken(auth);
        return new ResponseEntity<>(tokenService.getUser(auth),HttpStatus.OK);
    }

}