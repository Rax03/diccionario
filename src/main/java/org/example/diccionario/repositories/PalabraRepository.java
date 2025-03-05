package org.example.diccionario.repositories;

import org.example.diccionario.models.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Long> {

    @Query(
            value = "SELECT * FROM palabra AS pa WHERE pa.termino LIKE ?1%",
            nativeQuery = true
    )
    List<Palabra> getPalabrasByInitial(String comienzoPalabra);

    @Query(
            value = "SELECT * FROM palabra AS pa Where pa.categoriaGramatical = ?1",
            nativeQuery = true
    )
    List<Palabra> getPalabrasByCategoriaGramatical(String categoria);
}