package me.dio.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.domain.model.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {

    Optional<Editora> findByCnpj(String cpf);

    List<Editora> findAllByNome(String nome);

    boolean existsByCnpj(String cnpj);

}
