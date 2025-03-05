package org.example.diccionario.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "palabra")
public class Palabra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "termino", nullable = false)
    private String termino;

    @Size(max = 50)
    @NotNull
    @Column(name = "categoriagramatical", nullable = false, length = 50)
    private String categoriaGramatical;

    @JsonManagedReference
    @OneToMany(mappedBy = "palabra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Definicion> definicions = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public String getCategoriaGramatical() {
        return categoriaGramatical;
    }

    public void setCategoriaGramatical(String categoriaGramatical) {
        this.categoriaGramatical = categoriaGramatical;
    }

    public List<Definicion> getDefinicions() {
        return definicions;
    }

    public void setDefinicions(List<Definicion> definicions) {
        this.definicions = definicions;
    }

    @Override
    public String toString() {
        return "Palabra{" +
                "id=" + id +
                ", termino='" + termino + '\'' +
                ", categoriaGramatical='" + categoriaGramatical + '\'' +
                ", definicions=" + definicions +
                '}';
    }
}
