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
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Usuario> primeitoSelect(
            @PathVariable Long id
    ) {
        Optional<Usuario> usuario = repositorio.findById(id);

        if (usuario.isPresent()) {
            return ResponseEntity.ok().body(
                    usuario.get()
            );
        } else {
            return ResponseEntity.status(204).build();
        }
    }

    @GetMapping("/todos")
    @ResponseBody
    public ResponseEntity<List<Usuario>> segundoSelect() {
        List<Usuario> usuarios = repositorio.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.ok().body(
                    usuarios
            );
        }
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Usuario> atualizarSobrenome(
        @PathVariable Long id,
        @RequestParam String sobrenome
    ) {
        Optional<Usuario> usuario = repositorio.findById(id);

        if (usuario.isPresent()) {
            String nome = usuario.get().getNomeCompleto().split(" ")[0];

            usuario.get().setNomeCompleto(nome + " " + sobrenome);

            return ResponseEntity.ok().body(repositorio.save(usuario.get()));
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Usuario> atualizarEntidade(
            @PathVariable Long id,
            @Valid @RequestBody ContratoEntrada contratoEntrada
    ) {
        Optional<Usuario> usuario = repositorio.findById(id);

        if (usuario.isPresent()) {
            usuario.get().setNomeCompleto(contratoEntrada.getNome() + " " + contratoEntrada.getSobrenome());

            return ResponseEntity.ok().body(repositorio.save(usuario.get()));
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletasEntidade(
            @PathVariable Long id
    ) {
        Optional<Usuario> usuario = repositorio.findById(id);

        if (usuario.isPresent()) {
            repositorio.delete(usuario.get());

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
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