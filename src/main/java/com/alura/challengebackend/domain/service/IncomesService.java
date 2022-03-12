package com.alura.challengebackend.domain.service;

import com.alura.challengebackend.domain.exceptions.NegocioException;
import com.alura.challengebackend.domain.model.Expenses;
import com.alura.challengebackend.domain.model.Incomes;
import com.alura.challengebackend.domain.repository.ExpensesRepository;
import com.alura.challengebackend.domain.repository.IncomesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class IncomesService {

    private IncomesRepository repository;

    @Transactional
    public Incomes save(Incomes income) {
        boolean incomeDuplicate = repository.findByDescriptionAndReleaseDate(income.getDescription(), income.getReleaseDate())
                .stream()
                .anyMatch(e -> !e.equals(income));

        if (incomeDuplicate) {
            throw new NegocioException("Já existe essa receita cadastrada");
        }
        return repository.save(income);
    }
}
