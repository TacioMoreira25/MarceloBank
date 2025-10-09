package com.MarceloBank.MarceloBank.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Integer idCliente;

	@Column(unique = true)
    @Getter
    @Setter
    private String cpf;

    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String telefone;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Getter
    @Setter
    private String endereco;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @OneToMany(mappedBy = "cliente")
    @Getter
    @Setter
    private List<Conta> contas;

    @OneToMany(mappedBy = "cliente")
    @Getter
    @Setter
    private List<Emprestimo> emprestimos;

}
