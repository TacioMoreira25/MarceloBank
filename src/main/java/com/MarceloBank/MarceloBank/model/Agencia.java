package com.MarceloBank.MarceloBank.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "agencia")
@AllArgsConstructor
@NoArgsConstructor
public class Agencia
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer codigoAgencia;

    @Getter
    private String nomeAgencia;
    private String endereco;
    private String telefone;
    private String gerente;

    @OneToMany(mappedBy = "agencia")
    private List<Conta> contas = new ArrayList<>();
}
