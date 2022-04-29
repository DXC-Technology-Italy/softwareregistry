package com.cm.dev.controller;

import com.cm.dev.service.DependencyFileService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@CrossOrigin()
public class DependencyFileController {
    @Autowired
    private DependencyFileService dependencyFileService;


    @GetMapping("/file/dependencies/lines")
    public String getAll() {
        Gson gson = new Gson();
        HashMap<Integer, String> lines = new HashMap<>();
        try {
            lines = dependencyFileService.getAllLines();
        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage() );
        } finally {
            return gson.toJson(lines);
        }
    }

}
