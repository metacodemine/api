package org.eientei.codemine.api.controller;

import org.eientei.codemine.api.entity.UserEntity;
import org.eientei.codemine.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/users")
@RestController
public class UsersController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/token")
    public UserEntity token(@RequestParam("value") String value) {
        return userRepository.findFirstByToken(value);
    }

    @RequestMapping("/login")
    public UserEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserEntity userEntity = userRepository.findFirstByName(username);
        if (userEntity == null) {
            return null;
        }
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return null;
        }
        userEntity.setToken(UUID.randomUUID().toString());
        return userRepository.save(userEntity);
    }

    @RequestMapping("/signup")
    public UserEntity signup(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (userRepository.findFirstByName(username) != null) {
            return  null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setToken(UUID.randomUUID().toString());
        return userRepository.save(userEntity);
    }
}
