package org.example.diccionario.servicies;


import org.example.diccionario.exceptions.recordNotFoundException;
import org.example.diccionario.models.definicion;
import org.example.diccionario.models.palabra;
import org.example.diccionario.repositories.palabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class palabraService {
    @Autowired
    private palabraRepository palabraRepository;
    @Autowired
    private org.example.diccionario.repositories.definicionRepository definicionRepository;

    public List<palabra> getAllPalabras() {
        List<palabra> palabraLista = palabraRepository.findAll();
        if (palabraLista.size() > 0) {
            return palabraLista;
        } else {
            return new ArrayList<palabra>();
        }
    }

    public palabra getPalabraById(Long id) throws recordNotFoundException {
        Optional<palabra> palabra = palabraRepository.findById(id);

        if (palabra.isPresent()) {
            return palabra.get();
        } else {
            throw new recordNotFoundException("No ha palabra con esa id", id);
        }
    }

    public palabra createPalabra(palabra palabra) {
        if (palabra == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            return palabraRepository.save(palabra);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la palabra en la base de datos.", e);
        }
    }


    public palabra updatePalabra(Long id, palabra palabra) throws recordNotFoundException {
        palabra.setId(id);
        if (palabra.getId() == null) {
            throw new recordNotFoundException("Palabra sin identificador", 0L);
        }

        Optional<palabra> palabraOptional = palabraRepository.findById(id);

        if (palabraOptional.isPresent()) {
            palabra newPalabra = palabraOptional.get();
            newPalabra.setTermino(palabra.getTermino());
            newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());

            return palabraRepository.save(newPalabra);
        } else {
            throw new recordNotFoundException("No hay palabra con esa ID", palabra.getId());
        }
    }

    public void deletePalabra(Long id) throws recordNotFoundException {
        Optional<palabra> palabra = palabraRepository.findById(id);
        if (palabra.isPresent()) {
            palabraRepository.delete(palabra.get());
        } else {
            throw new recordNotFoundException("No ha palabra con esa id", id);
        }
    }

    public List<palabra> getByInitial(String comienzoPalabra) {
        List<palabra> palabraList = palabraRepository.getPalabrasByInitial(comienzoPalabra);
        if (palabraList.size() > 0) {
            return palabraList;
        } else {
            return new ArrayList<palabra>();
        }
    }

    public List<palabra> getByCategoria(String categoria) {
        List<palabra> palabraList = palabraRepository.getPalabrasByCategoriaGramatical(categoria);
        if (palabraList.size() > 0) {
            return palabraList;
        } else {
            return new ArrayList<palabra>();
        }
    }

    public palabra createPalabraDefinicion(palabra palabra) {
        if (palabra == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            palabra newPalabra = new palabra();
            newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());
            newPalabra.setTermino(palabra.getTermino());
            newPalabra = palabraRepository.save(newPalabra);

            List<definicion> definicions = palabra.getDefinicions();
            if (definicions.size() > 0) {
                for (definicion definicion : definicions) {
                    definicion.setPalabra(newPalabra);
                    definicionRepository.save(definicion);
                    newPalabra.getDefinicions().add(definicion);
                }
            }
            return newPalabra;
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la palabra en la base de datos.", e);
        }
    }
}