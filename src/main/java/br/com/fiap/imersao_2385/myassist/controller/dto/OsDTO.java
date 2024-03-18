package br.com.fiap.imersao_2385.myassist.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OsDTO {
    @NotBlank
    private String proprietario;

    @NotBlank
    private String tipoEquipamento;

    @NotNull
    private LocalDate entradaLab;

    @NotBlank
    private String defeito;

    private LocalDate previsaoEntrega;

    @NotBlank
    private String statusConcerto;

    private String observacoes;
}
