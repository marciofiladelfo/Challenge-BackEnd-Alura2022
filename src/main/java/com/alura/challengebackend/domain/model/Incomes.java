package com.alura.challengebackend.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "incomes")
public class Incomes {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO", nullable = false)
    private String description;

    @NotBlank
    @Column(name = "DATA_LANCAMENTO", nullable = false)
    private LocalDate releaseDate;

    @NotBlank
    @Column(name = "VALOR", nullable = false)
    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incomes)) return false;
        Incomes incomes = (Incomes) o;
        return id.equals(incomes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
