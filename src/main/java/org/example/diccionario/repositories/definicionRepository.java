package org.example.diccionario.repositories;

import org.example.diccionario.models.definicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface definicionRepository extends JpaRepository<definicion, Long> {
    @Query(
            value = "SELECT * FROM definicion AS de WHERE de.palabra_id = ?1",
            nativeQuery = true
    )
    List<definicion> getBypalabra(Long palabra_id);

}