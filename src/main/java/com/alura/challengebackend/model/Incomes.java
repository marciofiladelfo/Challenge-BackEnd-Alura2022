package com.alura.challengebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "incomes")
public class Incomes {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incomes)) return false;
        Incomes incomes = (Incomes) o;
        return Objects.equals(description, incomes.description) && Objects.equals(releaseDate, incomes.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, releaseDate, value);
    }
}
