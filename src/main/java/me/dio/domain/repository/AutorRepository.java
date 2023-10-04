package me.dio.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.domain.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByCpf(String cpf);

    List<Autor> findAllByNome(String nome);

    boolean existsByCpf(String cpf);

}
