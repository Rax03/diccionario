package org.example.diccionario.servicies;


import org.example.diccionario.exceptions.recordNotFoundException;
import org.example.diccionario.models.definicion;
import org.example.diccionario.models.palabra;
import org.example.diccionario.repositories.definicionRepository;
import org.example.diccionario.repositories.palabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class definicionService {


    @Autowired
    private definicionRepository definicionRepository;

    @Autowired
    private palabraRepository palabraRepository;

    public void deleteDefinicion(Long id) {
        Optional<definicion> definicion = definicionRepository.findById(id);
        if (definicion.isPresent()) {
            definicionRepository.delete(definicion.get());
        } else {
            throw new recordNotFoundException("No hay definicion con ese id", id);
        }
    }

    public List<definicion> getAllDefinicion(Long id) throws recordNotFoundException {
        List<definicion> definicionList = definicionRepository.getBypalabra(id);

        return definicionList;

    }

    public definicion createDefinicion(definicion definicion, Long id) {
        if (definicion == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            Optional<palabra> palabra = palabraRepository.findById(id);

            if (palabra.isPresent()) {
                palabra newpalabra = palabra.get();
                definicion newdefinicion = new definicion();
                newdefinicion.setPalabra(newpalabra);
                newdefinicion.setDescripcion(definicion.getDescripcion());
                newdefinicion.setEjemplo(definicion.getEjemplo());
                return definicionRepository.save(newdefinicion);
            } else {
                throw new recordNotFoundException("No ha palabra con esa id", id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la palabra en la base de datos.", e);
        }
    }
}