package io.github.sidneyrdm.clientes.rest;

import io.github.sidneyrdm.clientes.model.entity.Cliente;
import io.github.sidneyrdm.clientes.model.repository.ClienteRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoPrestadoRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repository;
    private final ServicoPrestadoRepository servicoRepository;

    @Autowired
    public ClienteController(ClienteRepository repository, ServicoPrestadoRepository servicoRepository) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Cliente> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar( @RequestBody @Valid Cliente cliente ){
        return repository.save(cliente);
    }

    @GetMapping("{id}")
    public Cliente acharPorId( @PathVariable Integer id ){

        System.out.println("entrou na busca pelo id= "+id);
        Integer novaID = null;
        try{
            System.out.println("Entrou no TRY");
           novaID = servicoRepository.acharPorId(id).getCliente().getId();
        }catch (NullPointerException e){
            System.out.println("Caiu no catch");
        }

        if(novaID == null){
            System.out.println("entrou no If");
            return repository
                    .findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }
        else {
            System.out.println("entrou no else");
            return repository
                    .findById(novaID)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map( cliente -> {
                repository.delete(cliente);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Cliente clienteAtualizado ) {
        repository
                .findById(id)
                .map( cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setInstagram(clienteAtualizado.getInstagram());
                    cliente.setFacebook(clienteAtualizado.getFacebook());
                    return repository.save(cliente);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @PutMapping("{id}/foto")
    public byte[] addFoto(@PathVariable Integer id, @RequestParam("foto") Part arquivo){
        Optional<Cliente> cliente = repository.findById(id);

        return cliente.map(cl -> {
            try{
                InputStream inputStream = arquivo.getInputStream(); //recebe o arquivo
                byte[] bytes = new byte[(int)arquivo.getSize()];
                IOUtils .readFully(inputStream, bytes); //pega o inputstream e joga dentro do array de bytes
                cl.setFoto(bytes);
                repository.save(cl);
                inputStream.close(); // boas práticas, sempre fechar o InputStream
                return bytes;
            }catch (IOException e){
                return null;
            }
        }).orElse(null);

    }
}
