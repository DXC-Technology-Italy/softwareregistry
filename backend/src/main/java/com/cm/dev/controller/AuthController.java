package com.cm.dev.controller;

import com.cm.dev.domain.User;
import com.cm.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<User> user(Authentication auth) {
        User user = new User();
        if (auth.isAuthenticated()) {
            try {
                user = userService.executeLogin(auth.getName());
            } catch (Exception e) {
                System.out.println("Exception : " + e.getMessage());
            }
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    /*
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic ".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

     */
}
