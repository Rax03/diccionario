package org.example.diccionario.controllers;

import org.example.diccionario.exceptions.RecordNotFoundException;
import org.example.diccionario.models.Definicion;
import org.example.diccionario.models.Palabra;
import org.example.diccionario.servicies.DefinicionService;
import org.example.diccionario.servicies.PalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/palabra")
public class PalabraServiceController {

    @Autowired
    private PalabraService palabraService;
    @Autowired
    private DefinicionService definicionService;

    @GetMapping
    public ResponseEntity<List<Palabra>> getAllPalabras() {
        List<Palabra> palabras = palabraService.getAllPalabras();

        return new ResponseEntity<List<Palabra>>(palabras, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabra(@PathVariable Long id)
            throws RecordNotFoundException {
        Palabra palabra = palabraService.getPalabraById(id);

        return new ResponseEntity<Palabra>(palabra, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Palabra> createPalabra(@RequestBody Palabra palabra) {
        Palabra nuevaPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.ok(nuevaPalabra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Palabra> updatePalabra(@PathVariable Long id, @RequestBody Palabra palabra)
            throws RecordNotFoundException {
        Palabra updatedPalabra = palabraService.updatePalabra(id,palabra);
        return ResponseEntity.ok(updatedPalabra);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePalabra(@PathVariable Long id)
            throws RecordNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}/definiciones")
    public ResponseEntity<List<Definicion>> palabraDefinicion(@PathVariable Long id) throws RecordNotFoundException {
        List<Definicion> definicion = definicionService.getAllDefinicion(id);

        return ResponseEntity.ok(definicion);
    }

    @PostMapping("/{id}/definiciones")
    public ResponseEntity<Definicion> createDefinicion(@RequestBody Definicion definicion, @PathVariable Long id){
        Definicion nuevaDefinicion = definicionService.createDefinicion(definicion, id);

        return ResponseEntity.ok(nuevaDefinicion);
    }

    @GetMapping("/inicial/{letra}")
    public ResponseEntity<List<Palabra>> inicialPalabras(@PathVariable String letra) {
        List<Palabra> palabraList = palabraService.getByInitial(letra);

        return ResponseEntity.ok(palabraList);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Palabra>> categoriaPalabras(@PathVariable String categoria) {
        List<Palabra> palabraList = palabraService.getByCategoria(categoria);

        return ResponseEntity.ok(palabraList);
    }

    @PostMapping("/con-definicion")
    public ResponseEntity<Palabra> palabraConDefinicion(@RequestBody Palabra palabra) {
        Palabra newPalabra = palabraService.createPalabraDefinicion(palabra);

        return ResponseEntity.ok(newPalabra);
    }
}