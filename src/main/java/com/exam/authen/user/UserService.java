package com.exam.authen.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser(User targetUser){
        return userRepository.save(targetUser);
    }

    public User getUserById(long targetUserId){
        return userRepository.getOne(targetUserId);
    }

    public User getUserByUsernameAndPassowrd(String username, String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean deleteUserById(long targetUserId){
        User targetUser = getUserById(targetUserId);
        try{
            userRepository.delete(targetUser);
        }catch(IllegalArgumentException e){
            return false;
        }
        return true;
    }
    
}