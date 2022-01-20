package com.alura.challengebackend.service;

import com.alura.challengebackend.exceptions.DatabaseException;
import com.alura.challengebackend.exceptions.ResourceNotFoundException;
import com.alura.challengebackend.model.Expenses;
import com.alura.challengebackend.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.Optional;

@Service
public class ExpensesService {

    @Autowired
    private ExpensesRepository repository;

    /**
     * Efetua busca no banco de dados e verifica contém a mesma descrição, dentro do mesmo mês.
     */
    @Transactional
    public Expenses insert(Expenses expenses) {
        var obj = repository.existsByDescriptionAndReleaseDate(expenses.getDescription(), expenses.getReleaseDate());
        if (!obj) {
            return repository.save(expenses);
        }
        return null;
    }

    @Transactional
    public Page<Expenses> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Expenses listById(BigInteger id) {
        Optional<Expenses> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void delete(BigInteger id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public Expenses update(BigInteger id, Expenses obj) {
        try {
            Expenses entity = repository.getById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Expenses entity, Expenses obj) {
        entity.setDescription(obj.getDescription());
        entity.setReleaseDate(obj.getReleaseDate());
        entity.setValue(obj.getValue());
    }
}
