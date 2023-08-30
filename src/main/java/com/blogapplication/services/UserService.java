package com.blogapplication.services;

import com.blogapplication.entity.User;
import com.blogapplication.repository.PostRepository;
import com.blogapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
  private UserRepository userRepository;

    public Optional<User> findByEmail(String email){
        return userRepository.findOneByEmail(email);
    }


    public User save(User user) {
      return userRepository.save(user);
    }

    public User findByName(String name) {
        Optional<User> optionalUser = userRepository.findByName(name);
        User user = new User();
        if(optionalUser.isPresent()){
            user = optionalUser.get();
        }
        return user;
    }


}
