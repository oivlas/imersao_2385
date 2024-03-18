package br.com.fiap.imersao_2385.myassist.repository;

import br.com.fiap.imersao_2385.myassist.entity.LoginEntity;
import br.com.fiap.imersao_2385.myassist.entity.OsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OsRepository extends JpaRepository<OsEntity, Long> {
}