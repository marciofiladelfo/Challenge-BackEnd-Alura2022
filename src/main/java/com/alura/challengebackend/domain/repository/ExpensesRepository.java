package com.alura.challengebackend.domain.repository;

import com.alura.challengebackend.domain.model.Expenses;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpensesRepository extends JpaRepository<Expenses, BigInteger> {

    Optional<Expenses> findByDescriptionAndReleaseDate(String description, LocalDate releaseDate);

    @NotNull Page<Expenses> findAll(@NotNull Pageable pageable);

    Optional<Expenses> findByDescription(String description);

    @Query(value = "SELECT * FROM expenses f WHERE YEAR(f.data_lancamento) = :ano AND  MONTH(f.data_lancamento)= :mes", nativeQuery = true)
    List<Expenses> findData(Integer ano, Integer mes);
}
