package com.alura.challengebackend.controller;

import com.alura.challengebackend.model.Incomes;
import com.alura.challengebackend.repository.IncomesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
public class IncomesController {

    @Autowired
    private IncomesRepository repository;

    @PostMapping(value = "receitas")
    @ResponseBody // Descrição da resposta
    public ResponseEntity<Object> saveIncomes(@RequestBody Incomes incomes) { //Recebe os dados para salvar
        var obj = repository.existsByDescriptionAndReleaseDate(incomes.getDescription(), incomes.getReleaseDate());
        if (!obj) {
            var receita = new Incomes();
            receita.setDescription(incomes.getDescription());
            receita.setReleaseDate(incomes.getReleaseDate());
            receita.setValue(incomes.getValue());
            repository.save(receita);
            return new ResponseEntity<>(receita, HttpStatus.CREATED);

        }
        return new ResponseEntity<>("Cadastro duplicado!", HttpStatus.CONFLICT);
    }

    @GetMapping(value = "receitas")
    @ResponseBody
    public ResponseEntity<Page<Incomes>> listIncomes(
            @PageableDefault(sort = "releaseDate", direction = Sort.Direction.DESC,
                    page = 0, size = 20) Pageable pageable) {
        var result = repository.findAll(pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "receitas/{id}")
    @ResponseBody
    public ResponseEntity<?> listIncomesById(@PathVariable BigInteger id) {
        var result = repository.findById(id);
        if (result.isPresent()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("ID not exist: " + id, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "receitas/{id}")
    public ResponseEntity<String> deleteIncomesById(@PathVariable BigInteger id) {
        var receita = repository.findById(id);
        if (receita.isPresent()) {
            repository.deleteById(id);
            return new ResponseEntity<>("Delete success id: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not exist: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "receitas/{id}")
    public ResponseEntity<?> updateIncomesById(@PathVariable BigInteger id, @RequestBody Incomes obj) {
        var receita = repository.findById(id)
                .map(entity -> {
                    entity.setDescription(obj.getDescription());
                    entity.setReleaseDate(obj.getReleaseDate());
                    entity.setValue(obj.getValue());
                    repository.save(entity);
                    return entity;
                });
        if (receita.isPresent()) {
            return new ResponseEntity<>("Update success id: " + id, HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not exist: " + id, HttpStatus.NOT_FOUND);
    }
}
