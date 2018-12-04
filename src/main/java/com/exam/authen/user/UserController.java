package com.exam.authen.user;

import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "hello SPring";
    }

    @PostMapping("/test/login")
    public ResponseEntity<User> testlogin(@RequestBody User givenUser){
        System.out.println("******************");
        System.out.println(givenUser.toString());
        User targetUser =  userService.getUserByUsernameAndPassowrd(givenUser.getUsername(), givenUser.getPassword());
        return new ResponseEntity<>(targetUser,HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User targetUser) {
        System.out.println("******************");
        System.out.println(targetUser.toString());
        User returnedUser = userService.createUser(targetUser);
        return new ResponseEntity<>(returnedUser,HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<User> update(@RequestBody User targetUser) {
        User returnedUser = userService.createUser(targetUser);
        return new ResponseEntity<>(returnedUser,HttpStatus.OK);
    }


    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable long userId) {
        boolean userRemoved = userService.deleteUserById(userId);
        return new ResponseEntity<>(userRemoved,HttpStatus.OK);
    }
    
	
}
