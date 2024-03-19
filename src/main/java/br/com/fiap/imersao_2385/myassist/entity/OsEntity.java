package br.com.fiap.imersao_2385.myassist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class OsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String proprietario;

    private String tipoEquipamento;

    private LocalDate entradaLab;

    private String defeito;

    private LocalDate previsaoEntrega;

    private String statusConcerto;

    private String observacoes;
}
