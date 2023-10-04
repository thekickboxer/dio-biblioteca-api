package me.dio.domain.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "livro")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    
    private int edicao;
    
    private int ano;

    private int numeroDePaginas;
    
    private String isbn;
    
    @ManyToOne
    @JoinColumn(name = "id_editora")
    private Editora editora;

    /*
    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
    })
    */
    @ManyToMany
    @JoinTable(
        name = "livro_autor",
        joinColumns = { @JoinColumn(name = "id_livro") },
        inverseJoinColumns = { @JoinColumn(name = "id_autor") }
    )
    private List<Autor> autores;
    
}
