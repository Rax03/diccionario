package org.example.diccionario.controllers;

import org.example.diccionario.exceptions.RecordNotFoundException;
import org.example.diccionario.servicies.DefinicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/definicion")
public class DefinicionServiceController {

    @Autowired
    DefinicionService service;

    @DeleteMapping("/{id}")
    public HttpStatus deleteDefinicion(@PathVariable Long id)
            throws RecordNotFoundException {
        service.deleteDefinicion(id);
        return HttpStatus.ACCEPTED;
    }
}