package io.github.sidneyrdm.clientes.rest;

import io.github.sidneyrdm.clientes.model.entity.Cliente;
import io.github.sidneyrdm.clientes.model.entity.Servico;
import io.github.sidneyrdm.clientes.model.entity.ServicoPrestado;
import io.github.sidneyrdm.clientes.model.repository.ClienteRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoPrestadoRepository;
import io.github.sidneyrdm.clientes.model.repository.ServicoRepository;
import io.github.sidneyrdm.clientes.rest.dto.ServicoPrestadoDTO;
import io.github.sidneyrdm.clientes.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Part;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController  {

    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;
    private final ServicoPrestadoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto ){

        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dataPg = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        //Preparando cliente
        Cliente cliente =
                clienteRepository
                        .findById(idCliente)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST, "Cliente inexistente."));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setSituacaoPagamento(dto.getSituacaoPagamento());
        if(dto.getDataPagamento() != null) servicoPrestado.setDataPagamento(dataPg);
        servicoPrestado.setData( data );
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor( bigDecimalConverter.converter(dto.getValor())  );

        return repository.save(servicoPrestado);
    }

     /* @GetMapping("/all")
    public List<ServicoPrestado> getAll() {
        return repository.findAll();
    }*/

    @GetMapping("/all")
    public Page<ServicoPrestado> getAll(
           @RequestParam(value = "page", defaultValue = "0") Integer pagina,
           @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina
    ) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina, sort);
        return repository.findAll(pageRequest);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }


    @GetMapping("{id}")
    public ServicoPrestado findByID( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço não encontrado") );
    }

    @GetMapping("/consulta")
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome
    ) {
        return repository.findByNomeCliente("%" + nome);
    }

    @GetMapping("/consulta/mes")
    public List<ServicoPrestado> getByMes(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Integer id ){
        repository
                .findById(id)
                .map( servicoPrestado -> {
                    repository.delete(servicoPrestado);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "servicoPrestado não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid ServicoPrestado servicoAtualizado ) {

        repository
                .findById(id)
                .map( servico -> {
                    servico.setDataPagamento(servicoAtualizado.getDataPagamento());
                    servico.setData(servicoAtualizado.getData());
                    servico.setCliente(servicoAtualizado.getCliente());
                    servico.setValor(servicoAtualizado.getValor());
                    servico.setDescricao(servicoAtualizado.getDescricao());
                    servico.setSituacaoPagamento(servicoAtualizado.getSituacaoPagamento());

                    return repository.save(servico);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado") );

    }


    @PutMapping("{id}/fotoAntes")
    public byte[] addFotoAntes(@PathVariable Integer id, @RequestParam("fotoAntes") Part arquivo){
        Optional<ServicoPrestado> servicoPrestado = repository.findById(id);

        return servicoPrestado.map(sp -> {
            try{
                InputStream inputStream = arquivo.getInputStream(); //recebe o arquivo
                byte[] bytes = new byte[(int)arquivo.getSize()];
                IOUtils.readFully(inputStream, bytes); //pega o inputstream e joga dentro do array de bytes
                sp.setFotoAntes(bytes);
                repository.save(sp);
                inputStream.close(); // boas práticas, sempre fechar o InputStream
                return bytes;
            }catch (IOException e){
                return null;
            }
        }).orElse(null);

    }

    @PutMapping("{id}/fotoDepois")
    public byte[] addFotoDepois(@PathVariable Integer id, @RequestParam("fotoDepois") Part arquivo){
        Optional<ServicoPrestado> servicoPrestado = repository.findById(id);

        return servicoPrestado.map(sp -> {
            try{
                InputStream inputStream = arquivo.getInputStream(); //recebe o arquivo
                byte[] bytes = new byte[(int)arquivo.getSize()];
                IOUtils.readFully(inputStream, bytes); //pega o inputstream e joga dentro do array de bytes
                sp.setFotoDepois(bytes);
                repository.save(sp);
                inputStream.close(); // boas práticas, sempre fechar o InputStream
                return bytes;
            }catch (IOException e){
                return null;
            }
        }).orElse(null);

    }
}
