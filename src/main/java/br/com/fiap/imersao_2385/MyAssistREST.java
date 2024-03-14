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

@Controller
@RequestMapping("/login")
public class MyAssistREST {
    @Autowired
    UsuarioRepository repositorio;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@Valid @RequestBody Login login) {
        UsuarioEntity entidade = new UsuarioEntity();

        entidade.setLogin(login.getLogin());
        entidade.setSenha(login.getSenha());

        return ResponseEntity.status(201).body(repositorio.save(entidade));
    }

    @GetMapping("/{login}/{senha}")
    @ResponseBody
    public ResponseEntity<UsuarioEntity> consultaPorLogin(
            @PathVariable String login,
            @PathVariable String senha
    ) {
        return ResponseEntity.ok().body(
                repositorio.findByLoginAndSenha(login, senha)
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
class Login{
    @NotBlank
    private String login;

    @NotBlank
    private String senha;
}

@Entity
@Data
class UsuarioEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String login;

    private String senha;
}

@Repository
interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
    UsuarioEntity findByLoginAndSenha(String login, String senha);
}