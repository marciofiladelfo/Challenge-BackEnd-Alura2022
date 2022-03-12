package com.alura.challengebackend.domain.repository;

import com.alura.challengebackend.domain.model.Incomes;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

public interface IncomesRepository extends JpaRepository<Incomes, BigInteger> {

    Optional<Incomes> findByDescriptionAndReleaseDate(String description, LocalDate releaseDate);
    @NotNull Page<Incomes> findAll(@NotNull Pageable pageable);
    Optional<Incomes> findByDescription(String description);
}
