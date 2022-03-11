package com.alura.challengebackend.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Expenses {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DESCRICAO")
    private String description;

    @NotBlank
    @Column(name = "DATA_LANCAMENTO")
    private LocalDate releaseDate;

    @NotBlank
    @Column(name = "VALOR")
    private Double value;

    @NotBlank
    @Size(max = 100)
    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Expenses)) return false;
        Expenses expenses = (Expenses) o;
        return id.equals(expenses.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
