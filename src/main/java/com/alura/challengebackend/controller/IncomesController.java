package com.alura.challengebackend.controller;

import com.alura.challengebackend.model.Incomes;
import com.alura.challengebackend.service.IncomesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
public class IncomesController {

    @Autowired
    private IncomesService service;

    @PostMapping(value = "receitas")
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Object> saveIncomes(@RequestBody Incomes incomes) { //Recebe os dados para salvar
        var income = service.insert(incomes);
        if (income == null) {
            return new ResponseEntity<>("Cadastro duplicado!", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(income, HttpStatus.CREATED);
    }

    @GetMapping(value = "receitas")
    @ResponseBody
    public ResponseEntity<Page<Incomes>> listIncomes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size) {
        var result = service.list(PageRequest.of(page, size, Sort.Direction.DESC, "releaseDate"));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "receitas/{id}")
    @ResponseBody
    public ResponseEntity<Incomes> listIncomesById(@PathVariable BigInteger id) {
        var result = service.listById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "receitas/{id}")
    public ResponseEntity<String> deleteIncomesById(@PathVariable BigInteger id) {
        service.delete(id);
        return new ResponseEntity<>("Delete success: " + id, HttpStatus.OK);
    }

    @PutMapping(value = "receitas/{id}")
    public ResponseEntity<Incomes> updateIncomesById(@PathVariable BigInteger id, @RequestBody Incomes obj) {
        obj = service.update(id, obj);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
}
