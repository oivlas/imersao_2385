package br.com.fiap.imersao_2385;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/primeira_chamada")
public class PrimeiraChamadaREST {

    @Autowired
    Repositorio repositorio;

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> primeiraChamada(){
        return ResponseEntity.ok().body("Primeira chamada com sucesso");
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Usuario> primeiroPost(@Valid @RequestBody ContratoEntrada contratoEntrada) {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto(contratoEntrada.getNome() + " " + contratoEntrada.getSobrenome());

        return ResponseEntity.ok().body(
                repositorio.save(usuario)
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

@Getter
@Setter
class ContratoEntrada{
    @NotBlank(message = "Informe o nome")
    private String nome;

    @NotBlank(message = "Informe o sobrenome")
    private String sobrenome;
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ContratoSaida{
    private String nomeCompleto;
}

@Entity
@Data
class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String nomeCompleto;
}

@Repository
interface Repositorio extends JpaRepository<Usuario, Long>{

}