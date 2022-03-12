package com.alura.challengebackend.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String description;

    @NotNull
    @Column(name = "DATA_LANCAMENTO")
    private LocalDate releaseDate;

    @NotNull
    @Column(name = "VALOR")
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
