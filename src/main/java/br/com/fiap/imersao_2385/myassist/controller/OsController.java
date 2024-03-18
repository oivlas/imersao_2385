package br.com.fiap.imersao_2385.myassist.controller;

import br.com.fiap.imersao_2385.myassist.controller.dto.OsAtualizacaoDTO;
import br.com.fiap.imersao_2385.myassist.controller.dto.OsDTO;
import br.com.fiap.imersao_2385.myassist.entity.OsEntity;
import br.com.fiap.imersao_2385.myassist.repository.OsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/os")
public class OsController {
    @Autowired
    OsRepository osRepository;

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<OsEntity>> consultarTodasOs() {
        List<OsEntity> listaOs = osRepository.findAll();

        if (listaOs.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        } else {
            return ResponseEntity.ok().body(listaOs);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<OsEntity> consultarOs(
            @PathVariable Long id
    ) {
        Optional<OsEntity> entidade = osRepository.findById(id);

        if (entidade.isPresent()) {
            return ResponseEntity.ok().body(entidade.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<OsEntity> cadastrarOS(
            @Valid @RequestBody OsDTO dto
            ) {
        OsEntity entidade = new OsEntity();

        entidade.setDefeito(dto.getDefeito());
        entidade.setProprietario(dto.getProprietario());
        entidade.setEntradaLab(dto.getEntradaLab());
        entidade.setTipoEquipamento(dto.getTipoEquipamento());
        entidade.setStatusConcerto(dto.getStatusConcerto());
        entidade.setObservacoes(dto.getObservacoes());

        return ResponseEntity.status(201).body(
                osRepository.save(entidade)
        );
    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ResponseEntity<OsEntity> atualizarOS(
            @PathVariable Long id,
            @Valid @RequestBody OsAtualizacaoDTO dto
    ) {
        Optional<OsEntity> entidade = osRepository.findById(id);

        if (entidade.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        } else {
            entidade.get().setPrevisaoEntrega(dto.getPrevisaoEntrega());
            entidade.get().setStatusConcerto(dto.getStatusConcerto());
            entidade.get().setObservacoes(dto.getObservacoes());

            return ResponseEntity.status(202).body(osRepository.save(entidade.get()));
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
