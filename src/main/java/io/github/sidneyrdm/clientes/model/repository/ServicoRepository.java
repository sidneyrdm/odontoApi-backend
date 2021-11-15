package io.github.sidneyrdm.clientes.model.repository;

import io.github.sidneyrdm.clientes.model.entity.Cliente;
import io.github.sidneyrdm.clientes.model.entity.Servico;
import io.github.sidneyrdm.clientes.model.entity.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Integer> {
}
