package com.alura.challengebackend.repository;

import com.alura.challengebackend.model.Expenses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

public interface ExpensesRepository extends JpaRepository<Expenses, BigInteger> {

    boolean existsByDescriptionAndReleaseDate(String description, LocalDate releaseDate);
    Page<Expenses> findAll(Pageable pageable);
    Optional<Expenses> findByDescription(String description);
}
