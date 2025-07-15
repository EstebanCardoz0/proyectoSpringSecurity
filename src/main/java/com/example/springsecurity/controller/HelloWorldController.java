package com.example.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

//    int[] notas = {10, 9, 8, 7, 6, 3, 2, 1};
//
//    @GetMapping("/notas")
//    public List<Integer> notas() {
//
//        List<Integer> listaNotas = new ArrayList<>();
//        for (int nota : notas) {
//            listaNotas.add(nota);
//        }
//        return listaNotas;
//    }
//
//    @GetMapping("/notas/promedio")
//    public double promedioNotas() {
//        double suma = 0;
//        for (int nota : notas) {
//            suma += nota;
//        }
//        return suma / notas.length;
//    }

}
