package com.alura.challengebackend.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "incomes")
public class Incomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "DATA_LANCAMENTO", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "VALOR", nullable = false)
    private Double value;

}
