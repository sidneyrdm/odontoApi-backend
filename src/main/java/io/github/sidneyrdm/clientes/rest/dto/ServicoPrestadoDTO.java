package io.github.sidneyrdm.clientes.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ServicoPrestadoDTO {

    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    private String descricao;

    @NotEmpty(message = "{campo.valor.obrigatorio}")
    private String valor;

    @NotEmpty(message = "{campo.data.obrigatorio}")
    private String data;

    private String dataPagamento;

    @NotNull(message = "{campo.cliente.obrigatorio}")
    private Integer idCliente;

    @NotEmpty(message = "{campo.situacaoPagamento.obrigatorio}")
    private String situacaoPagamento;
}
