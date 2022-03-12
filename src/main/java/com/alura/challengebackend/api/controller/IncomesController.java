package com.alura.challengebackend.api.controller;

import com.alura.challengebackend.domain.model.Incomes;
import com.alura.challengebackend.domain.repository.IncomesRepository;
import com.alura.challengebackend.domain.service.IncomesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@AllArgsConstructor
@RequestMapping("/receitas")
public class IncomesController {

    private IncomesRepository repository;
    private IncomesService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Incomes saveIncomes(@Valid @RequestBody Incomes incomes) { //Recebe os dados para salvar
        return service.save(incomes);
    }

    @GetMapping
    public Page<Incomes> listIncomes(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC,
                    size = 20) Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Incomes> listIncomesById(@PathVariable BigInteger id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"descricao"})
    public ResponseEntity<Incomes> listIncomesByDescription(
            @RequestParam(name = "descricao") String description) {
        return repository.findByDescription(description)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteIncomesById(@PathVariable BigInteger id) {
        if (!repository.existsById(id)) {
            ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateIncomesById(@PathVariable BigInteger id,
                                               @Valid @RequestBody Incomes obj) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        obj.setId(id);
        return ResponseEntity.ok(obj);
    }
}
