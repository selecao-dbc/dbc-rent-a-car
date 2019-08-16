package br.com.targettrust.traccadastros.entidades;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.targettrust.traccadastros.config.LocalDateDeserealizer;
import br.com.targettrust.traccadastros.config.LocalDateSerializer;


@Entity
@Table(name = "tb_reserva")
@AttributeOverrides({
        @AttributeOverride(name = "versao", column = @Column(name = "rsv_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_reserva", allocationSize = 1)
public class Reserva extends Entidade {

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @JsonDeserialize(using = LocalDateDeserealizer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_inicial")
    @Future
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy HH:mm:ss",
            timezone = "BRT")
    private LocalDate dataInicial;

    @JsonDeserialize(using = LocalDateDeserealizer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_final")
    @Future
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy HH:mm:ss",
            timezone = "BRT")
    private LocalDate dataFinal;

    @JsonDeserialize(using = LocalDateDeserealizer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dt_cancelamento")
    @PastOrPresent
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd.MM.yyyy HH:mm:ss",
            timezone = "BRT")
    private LocalDate dataCancelamento;

    @ManyToMany
    @JoinTable(name = "rl_reserva_equipamento",
            inverseJoinColumns = {@JoinColumn(name = "id_equipamento", referencedColumnName = "id")},
            joinColumns = {@JoinColumn(name = "id_reserva", referencedColumnName = "id")})
    private Set<Equipamento> equipamentos;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }

    public LocalDate getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDate dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public Set<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(Set<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }


}
