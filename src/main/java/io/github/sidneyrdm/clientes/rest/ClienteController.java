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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
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
                    cliente.setFace(clienteAtualizado.getFace());
                    cliente.setAlteracaoOclusao(clienteAtualizado.getAlteracaoOclusao());
                    cliente.setCep(clienteAtualizado.getCep());
                    cliente.setCidade(clienteAtualizado.getCidade());
                    cliente.setAlteracaoOclusaoTipo(clienteAtualizado.getAlteracaoOclusaoTipo());
                    cliente.setAntecedentesFamiliares(clienteAtualizado.getAntecedentesFamiliares());
                    cliente.setBebidasAlcoolicas(clienteAtualizado.getBebidasAlcoolicas());
                    cliente.setCoagulacao(clienteAtualizado.getCoagulacao());
                    cliente.setDataNascimento(cliente.getDataNascimento());
                    cliente.setDiabetes(clienteAtualizado.getDiabetes());
                    cliente.setDoencasSistemicas(clienteAtualizado.getDoencasSistemicas());
                    cliente.setSofreDoenca(clienteAtualizado.getSofreDoenca());
                    cliente.setSofreDoencaQual(clienteAtualizado.getSofreDoencaQual());
                    cliente.setSexo(clienteAtualizado.getSexo());
                    cliente.setSensibilidadeDentaria(clienteAtualizado.getSensibilidadeDentaria());
                    cliente.setProblemasAlergicos(clienteAtualizado.getProblemasAlergicos());
                    cliente.setProblemasAnestesia(clienteAtualizado.getProblemasAnestesia());
                    cliente.setProblemasArticulares(clienteAtualizado.getProblemasArticulares());
                    cliente.setProblemasCardiacos(clienteAtualizado.getProblemasCardiacos());
                    cliente.setProblemasCicatrizacao(clienteAtualizado.getProblemasCicatrizacao());
                    cliente.setProblemasGastricos(clienteAtualizado.getProblemasGastricos());
                    cliente.setProblemasHemorragia(clienteAtualizado.getProblemasHemorragia());
                    cliente.setProblemasRespiratorios(clienteAtualizado.getProblemasRespiratorios());
                    cliente.setProblemasRenais(clienteAtualizado.getProblemasRenais());
                    cliente.setPalato(clienteAtualizado.getPalato());
                    cliente.setProfissao(clienteAtualizado.getProfissao());
                    cliente.setProtese(clienteAtualizado.getProtese());
                    cliente.setProteseTipo(clienteAtualizado.getProteseTipo());
                    cliente.setNariz(clienteAtualizado.getNariz());
                    cliente.setNomeMae(clienteAtualizado.getNomeMae());
                    cliente.setNomeMedico(clienteAtualizado.getNomeMedico());
                    cliente.setNomePai(clienteAtualizado.getNomePai());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setEstadoCivil(clienteAtualizado.getEstadoCivil());
                    cliente.setOrgaoEmissor(clienteAtualizado.getOrgaoEmissor());
                    cliente.setFumante(clienteAtualizado.getFumante());
                    cliente.setFumanteQuantidade(clienteAtualizado.getFumanteQuantidade());
                    cliente.setFumanteTempo(clienteAtualizado.getFumanteTempo());
                    cliente.setFebreReumatica(clienteAtualizado.getFebreReumatica());
                    cliente.setGanglios(clienteAtualizado.getGanglios());
                    cliente.setGengivas(clienteAtualizado.getGengivas());
                    cliente.setGravidez(clienteAtualizado.getGravidez());
                    cliente.setGlandulasSalivares(clienteAtualizado.getGlandulasSalivares());
                    cliente.setHabitos(clienteAtualizado.getHabitos());
                    cliente.setHepatite(clienteAtualizado.getHepatite());
                    cliente.setHepatiteTipo(clienteAtualizado.getHepatiteTipo());
                    cliente.setHiv(clienteAtualizado.getHiv());
                    cliente.setHipertencaoArterialSistemica(clienteAtualizado.getHipertencaoArterialSistemica());
                    cliente.setInternacaoRecente(clienteAtualizado.getInternacaoRecente());
                    cliente.setJaFoiOperado(clienteAtualizado.getJaFoiOperado());
                    cliente.setJaFoiOperadoQuais(clienteAtualizado.getJaFoiOperadoQuais());
                    cliente.setLabios(clienteAtualizado.getLabios());
                    cliente.setLingua(clienteAtualizado.getLingua());
                    cliente.setMucosa(clienteAtualizado.getMucosa());
                    cliente.setObservacoesImportantes(clienteAtualizado.getObservacoesImportantes());
                    cliente.setQuais_alergias(clienteAtualizado.getQuais_alergias());
                    cliente.setQueixaAtual(clienteAtualizado.getQueixaAtual());
                    cliente.setQualMedicacao(clienteAtualizado.getQualMedicacao());
                    cliente.setRg(clienteAtualizado.getRg());
                    cliente.setReacoesAlergicas(clienteAtualizado.getReacoesAlergicas());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setTeveAlergia(clienteAtualizado.getTeveAlergia());
                    cliente.setTratamentoMedico(clienteAtualizado.getTratamentoMedico());
                    cliente.setTratamentosAnteriores(clienteAtualizado.getTratamentosAnteriores());
                    cliente.setUf(clienteAtualizado.getUf());
                    cliente.setUsoMedicacao(clienteAtualizado.getUsoMedicacao());
                    cliente.setUsoMedicacaoQuais(clienteAtualizado.getUsoMedicacaoQuais());
                    cliente.setUtilizaMedicacao(clienteAtualizado.getUtilizaMedicacao());
                    cliente.setVeioPor(clienteAtualizado.getVeioPor());

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
