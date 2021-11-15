package io.github.sidneyrdm.clientes.model.repository;

import io.github.sidneyrdm.clientes.model.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda, Integer> {

    @Query(" select nomeCliente, dataConsulta from Agenda where dataConsulta =:dataConsulta")
    List<Agenda> findByDataConsulta(
            @Param("dataConsulta") String dataConsulta);
}
