package br.com.fiap.imersao_2385.myassist.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank
    private String login;

    @NotBlank
    private String senha;
}
