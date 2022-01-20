package com.alura.challengebackend.controller;

import com.alura.challengebackend.model.Expenses;
import com.alura.challengebackend.model.Incomes;
import com.alura.challengebackend.service.ExpensesService;
import com.alura.challengebackend.service.IncomesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class ExpensesController {

    @Autowired
    private ExpensesService service;

    @PostMapping(value = "despesas")
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Object> saveIncomes(@RequestBody Expenses expenses) { //Recebe os dados para salvar
        var expense = service.insert(expenses);
        if (expense == null) {
            return new ResponseEntity<>("Cadastro duplicado!", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(expense, HttpStatus.CREATED);
    }

    @GetMapping(value = "despesas")
    @ResponseBody
    public ResponseEntity<Page<Expenses>> listIncomes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        var result = service.list(PageRequest.of(page, size, Sort.Direction.DESC, "releaseDate"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "despesas/{id}")
    @ResponseBody
    public ResponseEntity<Expenses> listIncomesById(@PathVariable BigInteger id) {
        var result = service.listById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "despesas/{id}")
    public ResponseEntity<String> deleteIncomesById(@PathVariable BigInteger id) {
        service.delete(id);
        return new ResponseEntity<>("Delete success: " + id, HttpStatus.OK);
    }

    @PutMapping(value = "despesas/{id}")
    public ResponseEntity<Expenses> updateIncomesById(@PathVariable BigInteger id, @RequestBody Expenses obj) {
        obj = service.update(id, obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
