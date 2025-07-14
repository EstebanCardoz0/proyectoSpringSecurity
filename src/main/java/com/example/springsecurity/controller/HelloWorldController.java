package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/holaSeg")
    public String secHelloWorld() {
        return "Hello, World! con seguridad";
    }

    @GetMapping("/holaNoSeg")
    public String noSecHelloWorld() {
        return "Hello, World! sin seguridad";
    }

}
