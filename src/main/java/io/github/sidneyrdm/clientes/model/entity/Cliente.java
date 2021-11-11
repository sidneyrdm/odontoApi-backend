package io.github.sidneyrdm.clientes.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false, length = 11)
    @NotNull(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String cpf;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

    @Column(length = 50)
    private String profissao;

    @Column(length = 50)
    private String telefone;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(length = 15)
    private String sexo;

    @Column(length = 10)
    private String rg;

    @Column(length = 30)
    private String orgaoEmissor;

    @Column(length = 50)
    private String endereco;

    @Column(length = 30)
    private String cidade;

    @Column(length = 2)
    private String uf;

    @Column(length = 20)
    private String cep;

    @Column(length = 15)
    private String estadoCivil;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String nomeMae;

    @Column(length = 50)
    private String nomePai;

    @Column(length = 50)
    private String veioPor;


    //Dados de saúde geral
    @Column(length = 4)
    private String febreReumatica;

    @Column(length = 4)
    private String hepatite;

    @Column(length = 50)
    private String hepatiteTipo;

    @Column(length = 4)
    private String diabetes;

    @Column(length = 4)
    private String hipertencaoArterialSistemica;

    @Column(length = 4)
    private String hiv;

    @Column(length = 4)
    private String coagulacao;

    @Column(length = 4)
    private String reacoesAlergicas;

    @Column(length = 4)
    private String doencasSistemicas;

    @Column(length = 50)
    private String tratamentosAnteriores;

    @Column(length = 4)
    private String internacaoRecente;


    @Column(length = 4)
    private String utilizaMedicacao;

    @Column(length = 50)
    private String qualMedicacao;

    @Column(length = 4)
    private String fumante;

    @Column(length = 50)
    private String fumanteQuantidade;

    @Column(length = 50)
    private String fumanteTempo;

    @Column(length = 4)
    private String bebidasAlcoolicas;

    @Column(length = 4)
    private String problemasCardiacos;

    @Column(length = 4)
    private String problemasRenais;

    @Column(length = 4)
    private String problemasGastricos;

    @Column(length = 4)
    private String problemasRespiratorios;

    @Column(length = 4)
    private String problemasAlergicos;

    @Column(length = 4)
    private String problemasArticulares;

    @Column(length = 50)
    private String queixaAtual;

    //Questionário de Saúde
    @Column(length = 4)
    private String sofreDoenca;

    @Column(length = 50)
    private String sofreDoencaQual;

    @Column(length = 4)
    private String tratamentoMedico;

    @Column(length = 4)
    private String gravidez;

    @Column(length = 4)
    private String usoMedicacao;

    @Column(length = 50)
    private String usoMedicacaoQuais;

    @Column(length = 50)
    private String nomeMedico;

    @Column(length = 4)
    private String teveAlergia;

    @Column(length = 150)
    private String quais_alergias;

    @Column(length = 4)
    private String jaFoiOperado;

    @Column(length = 150)
    private String jaFoiOperadoQuais;

    @Column(length = 4)
    private String problemasCicatrizacao;

    @Column(length = 4)
    private String problemasAnestesia;

    @Column(length = 4)
    private String problemasHemorragia;

    @Column(length = 150)
    private String habitos;

    @Column(length = 4)
    private String sensibilidadeDentaria;

    @Column(length = 150)
    private String antecedentesFamiliares;


    //Inspeção da boca e da face
    @Column()
    private String lingua;

    @Column(length = 150)
    private String mucosa;

    @Column(length = 150)
    private String palato;

    @Column(length = 150)
    private String labios;

    @Column(length = 150)
    private String gengivas;

    @Column(length = 150)
    private String nariz;

    @Column(length = 150)
    private String face;

    @Column(length = 150)
    private String ganglios;

    @Column(length = 150)
    private String glandulasSalivares;

    @Column(length = 80)
    private String alteracaoOclusao;

    @Column(length = 80)
    private String alteracaoOclusaoTipo;

    @Column(length = 4)
    private String protese;

    @Column(length = 80)
    private String proteseTipo;

    @Column
    private String observacoesImportantes;

    @Column(length = 50)
    private String facebook;

    @Column(length = 50)
    private String instagram;

    @Column
    @Lob
    private byte[] foto;

    @PrePersist
    public void prePersist(){
        setDataCadastro(LocalDate.now());
    }
}
