package org.example.diccionario.repositories;

import org.example.diccionario.models.palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface palabraRepository extends JpaRepository<palabra, Long> {

    @Query(
            value = "SELECT * FROM palabra AS pa WHERE pa.termino LIKE ?1%",
            nativeQuery = true
    )
    List<palabra> getPalabrasByInitial(String comienzoPalabra);

    @Query(
            value = "SELECT * FROM palabra AS pa Where pa.categoria_gramatical = ?1",
            nativeQuery = true
    )
    List<palabra> getPalabrasByCategoriaGramatical(String categoria);
}