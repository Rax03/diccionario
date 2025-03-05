package org.example.diccionario.repositories;

import org.example.diccionario.models.Definicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DefinicionRepository extends JpaRepository<Definicion, Long> {
    @Query(
            value = "SELECT * FROM definicion AS de WHERE de.palabra_id = ?1",
            nativeQuery = true
    )
    List<Definicion> getBypalabra(Long palabra_id);

}