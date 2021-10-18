package io.github.sidneyrdm.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

    @Column
    private BigDecimal valor;

    @Column(updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataPagamento;

    @Column(name = "situacao_pagamento", nullable = false)
    private String situacaoPagamento;

}
