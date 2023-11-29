package com.banking.application.service.impl;

import com.banking.application.dto.SignUpDto;
import com.banking.application.entity.User;
import com.banking.application.repository.UserRepository;
import com.banking.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(SignUpDto signUpDto) {
        User savedUser;
        User user = userRepository.findByFirstNameAndLastNameAndPhoneNumber(signUpDto.getFirst_name(), signUpDto.getLast_name(), signUpDto.getPhone_number());
        if (user != null) {
            savedUser = null;
        } else {
            User newUser = new User();
            newUser.setBalance(0);
            newUser.setEmail(signUpDto.getEmail());
            newUser.setFirstName(signUpDto.getFirst_name());
            newUser.setLastName(signUpDto.getLast_name());
            newUser.setPhoneNumber(signUpDto.getPhone_number());
            newUser.setAddress(signUpDto.getAddress());
            newUser.setPassword("NewPassword1");
            Random random = new Random();
            int uniqueNumber = random.nextInt(900) + 100;
            newUser.setAccountNumber(String.valueOf(uniqueNumber));
            newUser.setUsername(signUpDto.getFirst_name() + "." + signUpDto.getLast_name()+uniqueNumber);
            userRepository.save(newUser);
            savedUser= userRepository.findByAccountNumber(String.valueOf(uniqueNumber));
        }
        return savedUser;
    }

    @Override public String login(String username, String password) {
        User user = (User) userRepository.findByUsernameAndPassword(username,password);
        return user==null?"Wrong credentials":"success";
    }



}
