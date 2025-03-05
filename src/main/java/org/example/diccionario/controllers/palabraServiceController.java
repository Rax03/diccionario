package org.example.diccionario.controllers;

import org.example.diccionario.exceptions.recordNotFoundException;
import org.example.diccionario.models.definicion;
import org.example.diccionario.models.palabra;
import org.example.diccionario.servicies.definicionService;
import org.example.diccionario.servicies.palabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palabra")
public class palabraServiceController {

    @Autowired
    private palabraService palabraService;
    @Autowired
    private definicionService definicionService;

    @GetMapping
    public ResponseEntity<List<palabra>> getAllPalabras() {
        List<palabra> palabras = palabraService.getAllPalabras();

        return new ResponseEntity<List<palabra>>(palabras, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<palabra> getPalabra(@PathVariable Long id)
            throws recordNotFoundException {
        palabra palabra = palabraService.getPalabraById(id);

        return new ResponseEntity<palabra>(palabra, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<palabra> createPalabra(@RequestBody palabra palabra) {
        palabra nuevaPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.ok(nuevaPalabra);
    }

    @PutMapping("/{id}")
    public ResponseEntity<palabra> updatePalabra(@PathVariable Long id, @RequestBody palabra palabra)
            throws recordNotFoundException {
        palabra updatedPalabra = palabraService.updatePalabra(id,palabra);
        return ResponseEntity.ok(updatedPalabra);
    }

    // Nunca dejarlo bacio porque se borra toda la base de datos (Informacion)
    @DeleteMapping("/{id}")
    public HttpStatus deletePalabra(@PathVariable Long id)
            throws recordNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }

    @GetMapping("/{id}/definiciones")
    public ResponseEntity<List<definicion>> palabraDefinicion(@PathVariable Long id) throws recordNotFoundException {
        List<definicion> definicion = definicionService.getAllDefinicion(id);

        return ResponseEntity.ok(definicion);
    }

    @PostMapping("/{id}/definiciones")
    public ResponseEntity<definicion> createDefinicion(@RequestBody definicion definicion, @PathVariable Long id){
        definicion nuevaDefinicion = definicionService.createDefinicion(definicion, id);

        return ResponseEntity.ok(nuevaDefinicion);
    }

    @GetMapping("/inicial/{letra}")
    public ResponseEntity<List<palabra>> inicialPalabras(@PathVariable String letra) {
        List<palabra> palabraList = palabraService.getByInitial(letra);

        return ResponseEntity.ok(palabraList);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<palabra>> categoriaPalabras(@PathVariable String categoria) {
        List<palabra> palabraList = palabraService.getByCategoria(categoria);

        return ResponseEntity.ok(palabraList);
    }

    @PostMapping("/con-definicion")
    public ResponseEntity<palabra> palabraConDefinicion(@RequestBody palabra palabra) {
        palabra newPalabra = palabraService.createPalabraDefinicion(palabra);

        return ResponseEntity.ok(newPalabra);
    }
}