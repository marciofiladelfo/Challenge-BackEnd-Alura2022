package com.alura.challengebackend.api.controller;

import com.alura.challengebackend.domain.exceptions.EntidadeNaoEncontradaException;
import com.alura.challengebackend.domain.exceptions.NegocioException;
import com.alura.challengebackend.domain.model.Category;
import com.alura.challengebackend.domain.model.Expenses;
import com.alura.challengebackend.domain.repository.ExpensesRepository;
import com.alura.challengebackend.domain.service.ExpensesService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/despesas")
public class ExpensesController {

    private ExpensesRepository repository;
    private ExpensesService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expenses saveExpenses(@Valid @RequestBody Expenses expenses) { //Recebe os dados para salvar
        return service.save(expenses);
    }

    @GetMapping
    public Page<Expenses> listExpenses(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC,
                    size = 20) Pageable pageable) {
        return repository.findAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Expenses> listExpensesById(@PathVariable BigInteger id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Id não existe"));
    }

    @GetMapping(params = {"descricao"})
    public ResponseEntity<Expenses> listExpensesByDescription(
            @RequestParam(name = "descricao") String description) {
        return repository.findByDescription(description)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{ano}/{mes}")
    public List<Expenses> listExpensesDate(@PathVariable Integer ano, @PathVariable Integer mes) {
        return repository.findData(ano, mes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Expenses> deleteExpensesById(@PathVariable BigInteger id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Expenses> updateExpensesById(@PathVariable BigInteger id,
                                                       @Valid @RequestBody Expenses obj) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        obj.setId(id);
        service.save(obj);
        return ResponseEntity.ok(obj);
    }
}
