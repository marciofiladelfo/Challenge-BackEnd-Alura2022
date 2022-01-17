package com.alura.challengebackend.repository;

import com.alura.challengebackend.model.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ExpensesRepository extends JpaRepository<Expenses, BigInteger> {
}
