package br.com.fiap.imersao_2385.myassist.repository;

import br.com.fiap.imersao_2385.myassist.entity.LoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, Long> {
    Optional<LoginEntity> findByLoginAndSenha(String login, String senha);
}