package com.alura.challengebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false)
    private BigInteger id;

    @Column(name = "DESCRICAO", updatable = false)
    private String description;

    @Column(name = "DATA_LANCAMENTO", updatable = false)
    private LocalDate releaseDate;

    @Column(name = "VALOR", updatable = false)
    private Double value;
}
