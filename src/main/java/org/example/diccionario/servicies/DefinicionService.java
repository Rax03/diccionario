package org.example.diccionario.servicies;


import org.example.diccionario.exceptions.RecordNotFoundException;
import org.example.diccionario.models.Definicion;
import org.example.diccionario.models.Palabra;
import org.example.diccionario.repositories.DefinicionRepository;
import org.example.diccionario.repositories.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefinicionService {


    @Autowired
    private DefinicionRepository definicionRepository;

    @Autowired
    private PalabraRepository palabraRepository;

    public void deleteDefinicion(Long id) {
        Optional<Definicion> definicion = definicionRepository.findById(id);
        if (definicion.isPresent()) {
            definicionRepository.delete(definicion.get());
        } else {
            throw new RecordNotFoundException("No hay definicion con ese id", id);
        }
    }

    public List<Definicion> getAllDefinicion(Long id) throws RecordNotFoundException {
        List<Definicion> definicionList = definicionRepository.getBypalabra(id);

        return definicionList;

    }

    public Definicion createDefinicion(Definicion definicion, Long id) {
        if (definicion == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            Optional<Palabra> palabra = palabraRepository.findById(id);

            if (palabra.isPresent()) {
                Palabra newpalabra = palabra.get();
                Definicion newdefinicion = new Definicion();
                newdefinicion.setPalabra(newpalabra);
                newdefinicion.setDescripcion(definicion.getDescripcion());
                newdefinicion.setEjemplo(definicion.getEjemplo());
                return definicionRepository.save(newdefinicion);
            } else {
                throw new RecordNotFoundException("No ha palabra con esa id", id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la palabra en la base de datos.", e);
        }
    }
}