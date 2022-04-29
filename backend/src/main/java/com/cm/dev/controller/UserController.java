package com.cm.dev.controller;

import com.cm.dev.domain.User;
import com.cm.dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/updateTheme", method = RequestMethod.POST)
    public void updateTheme(@RequestParam(value = "username") String username,
                            @RequestParam(value = "theme") String theme) {
        try {
            userService.updateUserTheme(username, theme);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

    }

    @RequestMapping(value = "/user")
    public List<User> getAllUses() {
        List<User> users = new ArrayList<>();
        try {
            users = userService.getAllUsers();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            return users;
        }

    }

    @RequestMapping(value = "/user/updateRole", method = RequestMethod.POST)
    public void updateRole(@RequestParam(value = "username") String username,
                           @RequestParam(value = "role") String role) {
        try {
            userService.updateUserRole(username, role);
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
        }

    }

    /*
    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic ".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }

     */
}
