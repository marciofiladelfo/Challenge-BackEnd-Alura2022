package com.alura.challengebackend.domain.service;

import com.alura.challengebackend.domain.exceptions.NegocioException;
import com.alura.challengebackend.domain.model.Expenses;
import com.alura.challengebackend.domain.repository.ExpensesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ExpensesService {

    private ExpensesRepository repository;

    @Transactional
    public Expenses salvar(Expenses expense) {
        boolean expenseDuplicate = repository.findByDescriptionAndReleaseDate(expense.getDescription(), expense.getReleaseDate())
                .stream()
                .anyMatch(e -> !e.equals(expense));

        if (expenseDuplicate) {
            throw new NegocioException("Já existe essa despesa cadastrada");
        }
        return repository.save(expense);
    }
}
