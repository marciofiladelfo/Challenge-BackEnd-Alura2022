package com.alura.challengebackend.service;

import com.alura.challengebackend.model.Incomes;
import com.alura.challengebackend.repository.IncomesRepository;
import com.alura.challengebackend.service.exceptions.DatabaseException;
import com.alura.challengebackend.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class IncomesService {

    @Autowired
    private IncomesRepository repository;

    /**
     * Efetua busca no banco de dados e verifica contém a mesma descrição, dentro do mesmo mês.
     */

    @Transactional
    public Incomes insert(Incomes incomes) {
        var obj = repository.existsByDescriptionAndReleaseDate(incomes.getDescription(), incomes.getReleaseDate());
        if (!obj) {
            return repository.save(incomes);
        }
        return null;
    }

    @Transactional
    public List<Incomes> list() {
        return repository.findAll();
    }

    public Incomes listById(BigInteger id) {
        Optional<Incomes> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public void delete(BigInteger id) {
        try {
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        }catch(DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Incomes update(BigInteger id, Incomes obj) {
        try {
            Incomes entity = repository.getOne(id);
            updateData(entity, obj);
            return repository.save(entity);
        }catch(EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Incomes entity, Incomes obj) {
        entity.setDescription(obj.getDescription());
        entity.setReleaseDate(obj.getReleaseDate());
        entity.setValue(obj.getValue());
    }
}
