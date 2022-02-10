package com.alura.challengebackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Data
@Table(name = "expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @Column(name = "DATA_LANCAMENTO", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "VALOR", nullable = false)
    private Double value;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    public Expenses(BigInteger id, String description, LocalDate releaseDate, Double value, Category category) {
        this.id = id;
        this.description = description;
        this.releaseDate = releaseDate;
        this.value = value;
        this.category = category;
    }
}
