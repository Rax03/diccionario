package org.example.diccionario.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "palabra")
public class palabra {
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
    @Column(name = "categoriaGramatical", nullable = false, length = 50)
    private String categoriaGramatical;

    @JsonManagedReference
    @OneToMany(mappedBy = "palabra")
    private List<Definicion> definicions = new ArrayList<>();

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