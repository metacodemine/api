package org.eientei.codemine.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.eientei.codemine.api.data.TextureProperties;
import org.eientei.codemine.api.data.TextureProperty;
import org.eientei.codemine.api.data.UserProperties;
import org.eientei.codemine.api.data.UserProperty;
import org.eientei.codemine.api.entity.UserEntity;
import org.eientei.codemine.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.*;
import java.util.*;

@RequestMapping("/users")
@RestController
public class UsersController {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @RequestMapping("/token")
    public UserEntity token(@RequestParam("value") String value) {
        return userRepository.findFirstByToken(value);
    }

    @Transactional
    @RequestMapping("/login")
    public UserEntity login(@RequestParam("username") String username, @RequestParam("password") String password) {
        UserEntity userEntity = userRepository.findFirstByName(username);
        if (userEntity == null) {
            return null;
        }
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return null;
        }
        return userRepository.save(userEntity);
    }

    @Transactional
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

    @Transactional
    @RequestMapping(value = "/texture/{value}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] texture(@PathVariable("value") String value) {
        UserEntity userEntity = userRepository.findFirstByToken(value);
        if (userEntity == null) {
            return null;
        }
        return userEntity.getSkin();
    }

    @Transactional
    @RequestMapping(value = "/setskin/{value}", method = RequestMethod.POST)
    public boolean setskin(@PathVariable("value") String value, @RequestBody byte[] body) {
        UserEntity userEntity = userRepository.findFirstByToken(value);
        if (userEntity == null) {
            return false;
        }
        if (body.length > 4096) {
            return false;
        }

        userEntity.setSkin(body);
        userRepository.save(userEntity);
        return true;
    }

    @Transactional
    @RequestMapping("/session")
    public UserProperties session(@RequestParam("value") String value) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        UserEntity userEntity = userRepository.findFirstByToken(value.replaceAll("(.{8})(.{4})(.{4})(.{4})(.{12})", "$1-$2-$3-$4-$5"));
        if (userEntity == null) {
            userEntity = userRepository.findFirstByName(value);
            if (userEntity == null) {
                return null;
            }
        }

        TextureProperty texture = new TextureProperty();
        texture.setUrl("https://mock.mojang.com/api/users/texture/" + userEntity.getToken());

        Map<String, TextureProperty> map = new HashMap<String, TextureProperty>();
        map.put("SKIN", texture);

        TextureProperties textureProperties = new TextureProperties();
        textureProperties.setTimestamp(new Date().getTime());
        textureProperties.setProfileId(userEntity.getToken());
        textureProperties.setProfileName(userEntity.getName());
        textureProperties.setTextures(map);
        String str = objectMapper.writeValueAsString(textureProperties);
        String b64 = Base64.encodeBase64String(str.getBytes());

        UserProperties properties = new UserProperties();
        properties.setId(userEntity.getToken());
        properties.setName(userEntity.getName());

        UserProperty property = new UserProperty();
        property.setName("textures");
        property.setValue(b64);

        properties.setProperties(Collections.singletonList(property));
        return properties;
    }
}
