package io.github.sidneyrdm.clientes.rest;

import io.github.sidneyrdm.clientes.model.entity.Agenda;
import io.github.sidneyrdm.clientes.model.entity.Cliente;
import io.github.sidneyrdm.clientes.model.entity.Servico;
import io.github.sidneyrdm.clientes.model.entity.ServicoPrestado;
import io.github.sidneyrdm.clientes.model.repository.AgendaRepository;
import io.github.sidneyrdm.clientes.model.repository.ClienteRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoPrestadoRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaRepository repository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public AgendaController(AgendaRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public List<Agenda> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agenda salvar( @RequestBody @Valid Agenda agenda ){
        return repository.save(agenda);
    }


    @GetMapping("{id}")
    public Agenda findByID(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda não encontrado") );
    }

    @GetMapping("/data")
    public List<Agenda> findByDataConsulta(
            @RequestParam(value = "dataConsulta", required = false, defaultValue = "") String dataConsulta
    ) {
        return repository.findByDataConsulta(dataConsulta);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map( agenda -> {
                repository.delete(agenda);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Agenda agendaAtualizada ) {
        repository
                .findById(id)
                .map( agenda -> {
                    agenda.setNomeCliente(agendaAtualizada.getNomeCliente());
                    agenda.setDataConsulta(agendaAtualizada.getDataConsulta());

                    return repository.save(agenda);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Agenda nao encontrado") );
    }
}
