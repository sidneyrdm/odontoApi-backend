package io.github.sidneyrdm.clientes.rest;

import io.github.sidneyrdm.clientes.model.entity.Cliente;
import io.github.sidneyrdm.clientes.model.entity.Servico;
import io.github.sidneyrdm.clientes.model.entity.ServicoPrestado;
import io.github.sidneyrdm.clientes.model.repository.ClienteRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoPrestadoRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository repository;
    private final ServicoPrestadoRepository servicoRepository;

    @Autowired
    public ServicoController(ServicoRepository repository, ServicoPrestadoRepository servicoRepository) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Servico> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Servico salvar( @RequestBody @Valid Servico servico ){
        return repository.save(servico);
    }


    @GetMapping("{id}")
    public Servico findByID(@PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado") );
    }


    /*
    @GetMapping("{id}")
    public Servico acharPorId( @PathVariable Integer id ){

        Integer novaID = null;
        try{
           novaID = servicoRepository.acharPorId(id).getCliente().getId();
        }catch (NullPointerException e){
            System.out.println(e);
        }

        if(novaID == null){
            return repository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado"));
        }
        else {
            System.out.println("entrou no else");
            return repository
                    .findById(novaID)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado"));
        }
    }*/

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map( servico -> {
                repository.delete(servico);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Servico servicoAtualizado ) {
        repository
                .findById(id)
                .map( servico -> {
                    servico.setDescricao(servicoAtualizado.getDescricao());
                    servico.setValor(servicoAtualizado.getValor());

                    return repository.save(servico);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado") );
    }
}
