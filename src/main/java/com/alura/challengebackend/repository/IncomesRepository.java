package com.alura.challengebackend.repository;

import com.alura.challengebackend.model.Incomes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface IncomesRepository extends JpaRepository<Incomes, BigInteger> {
}
