package com.alura.challengebackend.repository;

import com.alura.challengebackend.model.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.LocalDate;

public interface IncomesRepository extends JpaRepository<Incomes, BigInteger> {

    boolean existsByDescriptionAndReleaseDate(String description, LocalDate releaseDate);
}
