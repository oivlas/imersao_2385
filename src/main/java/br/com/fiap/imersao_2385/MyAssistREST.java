package br.com.fiap.imersao_2385;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class MyAssistREST {

    @Autowired
    LoginRepository repository;

    @PostMapping("/cadastrar")
    @ResponseBody
    public ResponseEntity<LoginEntity> cadastrarLogin(@Valid @RequestBody LoginDTO dto) {
        LoginEntity entity = new LoginEntity();

        entity.setLogin(dto.getLogin());
        entity.setSenha(dto.getSenha());

        return ResponseEntity.status(201).body(
                repository.save(entity)
        );
    }

    @PutMapping("/cadastrar/{id}")
    @ResponseBody
    public ResponseEntity<LoginEntity> sobrescreverLogin(
            @PathVariable Long id,
            @Valid @RequestBody LoginDTO dto
    ) {
        Optional<LoginEntity> entity = repository.findById(id);

        if (entity.isPresent()) {
            entity.get().setSenha(dto.getSenha());
            entity.get().setLogin(dto.getLogin());

            return ResponseEntity.accepted().body(
                    repository.save(entity.get())
            );
        } else {
            return ResponseEntity.unprocessableEntity().body(null);
        }
    }

    @DeleteMapping("/cadastrar/{id}")
    public ResponseEntity deletarLogin(
            @PathVariable Long id
    ) {
        Optional<LoginEntity> entity = repository.findById(id);

        if (entity.isPresent()) {
            repository.delete(entity.get());

            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @GetMapping("/autenticacao")
    public ResponseEntity autenticarLogin(
            @RequestParam String login,
            @RequestParam String senha
    ) {
        Optional<LoginEntity> entity = repository.findByLoginAndSenha(login, senha);

        if (entity.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build();
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
class LoginDTO {
    @NotBlank
    private String login;

    @NotBlank
    private String senha;
}

@Entity
@Data
class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String login;

    private String senha;
}

@Repository
interface LoginRepository extends JpaRepository<LoginEntity, Long>{
    Optional<LoginEntity> findByLoginAndSenha(String login, String senha);
}