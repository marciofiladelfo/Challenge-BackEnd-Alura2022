package com.alura.challengebackend.controller;

import com.alura.challengebackend.model.Category;
import com.alura.challengebackend.model.Expenses;
import com.alura.challengebackend.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class ExpensesController {

    @Autowired
    private ExpensesRepository repository;

    @PostMapping(value = "despesas")
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Object> saveExpenses(@RequestBody Expenses expenses) { //Recebe os dados para salvar
        var obj = repository.existsByDescriptionAndReleaseDate(expenses.getDescription(), expenses.getReleaseDate());
        if (expenses.getCategory() == null) {
            expenses.setCategory(Category.OUTROS);
        } else {
            return new ResponseEntity<>(errorPage(), HttpStatus.NOT_FOUND);
        }
        if (!obj) {
            var despesa = new Expenses();
            despesa.setDescription(expenses.getDescription());
            despesa.setReleaseDate(expenses.getReleaseDate());
            despesa.setValue(expenses.getValue());
            despesa.setCategory(expenses.getCategory());
            repository.save(despesa);
            return new ResponseEntity<>(despesa, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Cadastro duplicado!", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public String errorPage() {
        String categorias = "[ALIMENTACAO, SAUDE, MORADIA, TRANSPORTE, EDUCACAO, LAZER, IMPREVISTOS, OUTROS]";
        return "Categoria não existe!\n" + "Inserir apenas as seguintes categorias: "
                + categorias;
    }

    @GetMapping(value = "despesas")
    @ResponseBody
    public ResponseEntity<Page<Expenses>> listExpenses(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC,
                    page = 0, size = 20) Pageable pageable) {
        var result = repository.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "despesas/{id}")
    @ResponseBody
    public ResponseEntity<?> listExpensesById(@PathVariable BigInteger id) {
        var result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("ID not exist: " + id, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    @Cacheable(value = "despesas")
    public ResponseEntity<?> listExpensesByDescription(
            @RequestParam String description) {
        var result = repository.findByDescription(description);
        if (result.isPresent()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Description not exist: " + description, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "despesas/{id}")
    public ResponseEntity<String> deleteExpensesById(@PathVariable BigInteger id) {
        var despesa = repository.findById(id);
        if (despesa.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Delete success id: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not exist: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "despesas/{id}")
    public ResponseEntity<?> updateExpensesById(@PathVariable BigInteger id, @RequestBody Expenses obj) {
        var despesa = repository.findById(id)
                .map(entity -> {
                    entity.setDescription(obj.getDescription());
                    entity.setReleaseDate(obj.getReleaseDate());
                    entity.setValue(obj.getValue());
                    if (obj.getCategory() == null) {
                        obj.setCategory(Category.OUTROS);
                    } else {
                        entity.setCategory(obj.getCategory());
                    }
                    repository.save(entity);
                    return entity;
                });
        if (despesa.isPresent()) {
            return new ResponseEntity<>("Update success id: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not exist: " + id, HttpStatus.NOT_FOUND);
    }
}
