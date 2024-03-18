package br.com.fiap.imersao_2385.myassist.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OsAtualizacaoDTO {
    private LocalDate previsaoEntrega;

    private String statusConcerto;

    private String observacoes;
}
