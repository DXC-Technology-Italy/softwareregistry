package com.cm.dev.controller;

import com.cm.dev.domain.User;
import com.cm.dev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller that manages the login process to the system using an LDAP backend
 * 
 */

@RestController
@CrossOrigin
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    /**
     * Return all Area from the collection
     *
     *  @param auth - the object Authentication
     *
     * @return ResponseEntity<User>
     */
    @PostMapping(value = "/login")
    public ResponseEntity<User> user(Authentication auth) {
        User user = new User();
        if (auth.isAuthenticated()) {
            try {
                user = userService.executeLogin(auth.getName());
            } catch (Exception e) {
                LOGGER.error(String.valueOf(e));
            }
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
