package io.github.sidneyrdm.clientes.rest;

import io.github.sidneyrdm.clientes.exception.UsuarioCadastradoException;
import io.github.sidneyrdm.clientes.model.entity.Usuario;
import io.github.sidneyrdm.clientes.model.repository.UsuarioRepository;
import io.github.sidneyrdm.clientes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final UsuarioRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario usuario){
        try{
            service.salvar(usuario);
        }catch (UsuarioCadastradoException e){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage() );
        }
    }

    @GetMapping
    public List<Usuario> listAll(){
        return this.repository.findAll();
    }
}
