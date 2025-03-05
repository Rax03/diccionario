package org.example.diccionario.servicies;


import org.example.diccionario.exceptions.RecordNotFoundException;
import org.example.diccionario.models.Definicion;
import org.example.diccionario.models.Palabra;
import org.example.diccionario.repositories.DefinicionRepository;
import org.example.diccionario.repositories.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PalabraService {
    @Autowired
    private PalabraRepository palabraRepository;
    @Autowired
    private DefinicionRepository definicionRepository;

    public List<Palabra> getAllPalabras() {
        List<Palabra> palabraLista = palabraRepository.findAll();
        if (palabraLista.size() > 0) {
            return palabraLista;
        } else {
            return new ArrayList<Palabra>();
        }
    }

    public Palabra getPalabraById(Long id) throws RecordNotFoundException {
        Optional<Palabra> palabra = palabraRepository.findById(id);

        if (palabra.isPresent()) {
            return palabra.get();
        } else {
            throw new RecordNotFoundException("No ha palabra con esa id", id);
        }
    }

    public Palabra createPalabra(Palabra palabra) {
        if (palabra == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            return palabraRepository.save(palabra);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar la palabra en la base de datos.", e);
        }
    }


    public Palabra updatePalabra(Long id, Palabra palabra) throws RecordNotFoundException {
        palabra.setId(id);
        if (palabra.getId() == null) {
            throw new RecordNotFoundException("Palabra sin identificador", 0L);
        }

        Optional<Palabra> palabraOptional = palabraRepository.findById(id);

        if (palabraOptional.isPresent()) {
            Palabra newPalabra = palabraOptional.get();
            newPalabra.setTermino(palabra.getTermino());
            newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());

            return palabraRepository.save(newPalabra);
        } else {
            throw new RecordNotFoundException("No hay palabra con esa ID", palabra.getId());
        }
    }

    public void deletePalabra(Long id) throws RecordNotFoundException {
        Optional<Palabra> palabra = palabraRepository.findById(id);
        if (palabra.isPresent()) {
            palabraRepository.delete(palabra.get());
        } else {
            throw new RecordNotFoundException("No ha palabra con esa id", id);
        }
    }

    public List<Palabra> getByInitial(String comienzoPalabra) {
        List<Palabra> palabraList = palabraRepository.getPalabrasByInitial(comienzoPalabra);
        if (palabraList.size() > 0) {
            return palabraList;
        } else {
            return new ArrayList<Palabra>();
        }
    }

    public List<Palabra> getByCategoria(String categoria) {
        List<Palabra> palabraList = palabraRepository.getPalabrasByCategoriaGramatical(categoria);
        if (palabraList.size() > 0) {
            return palabraList;
        } else {
            return new ArrayList<Palabra>();
        }
    }

    public Palabra createPalabraDefinicion(Palabra palabra) {
        if (palabra == null) {
            throw new IllegalArgumentException("La palabra no puede ser nula.");
        }

        try {
            Palabra newPalabra = new Palabra();
            newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());
            newPalabra.setTermino(palabra.getTermino());
            newPalabra = palabraRepository.save(newPalabra);

            List<Definicion> definicions = palabra.getDefinicions();
            if (definicions.size() > 0) {
                for (Definicion definicion : definicions) {
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