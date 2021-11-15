package io.github.sidneyrdm.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.nomeCliente.obrigatorio}")
    private String nomeCliente;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "{campo.dataConsulta.obrigatorio}")
    private LocalDate dataConsulta;
}
