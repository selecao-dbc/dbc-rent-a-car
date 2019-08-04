package br.com.targettrust.traccadastros.entidades;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name="tb_locacao")
@AttributeOverrides({
	@AttributeOverride(name="versao", column=@Column(name="loc_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_locacao", allocationSize = 1)
public class Locacao extends Entidade{
	
	@ManyToOne
	@JoinColumn(name="id_veiculo")
	@NotNull
	private Veiculo veiculo;
	
	@Column(name="dt_inicio")
	@FutureOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate dataInicial;
	
	@Column(name="dt_fim")
	@Future
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
	private LocalDate dataFinal;
	
	@Column(name="vlr_pago")
	@NotNull
	@Min(1)
	private Double valor;

	@ManyToMany
	@JoinTable(name = "rl_locacao_equipamento", 
		inverseJoinColumns = {@JoinColumn(name = "id_equipamento", referencedColumnName = "id") }, 
		joinColumns = {@JoinColumn(name = "id_locacao", referencedColumnName = "id") } )
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
	
}
