package com.cm.dev.controller;

import com.cm.dev.domain.User;
import com.cm.dev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for User Objects
 * 
 */
@RestController
@CrossOrigin
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    
    /** 
     * @param "username"
     */
    @PostMapping(value = "/user/updateTheme")
    public void updateTheme(@RequestParam(value = "username") String username,
                            @RequestParam(value = "theme") String theme) {
        try {
            userService.updateUserTheme(username, theme);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }

    }

    
    /** 
     * @return List<User>
     */
    @RequestMapping(value = "/user")
    public List<User> getAllUses() {
        List<User> users = new ArrayList<>();
        try {
            users = userService.getAllUsers();
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }
        return users;
    }

    
    /** 
     * @param "username"
     */
    @PostMapping(value = "/user/updateRole")
    public void updateRole(@RequestParam(value = "username") String username,
                           @RequestParam(value = "role") String role) {
        try {
            userService.updateUserRole(username, role);
        } catch (Exception e) {
            LOGGER.error(String.valueOf(e));
        }

    }

}
