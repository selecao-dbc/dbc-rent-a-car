package br.com.targettrust.traccadastros.entidades;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="tb_veiculo")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
@AttributeOverrides({
	@AttributeOverride(name="versao", column=@Column(name="vcl_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_veiculo", allocationSize = 1)
public abstract class Veiculo extends Entidade {

	@Column(name="vcl_placa", length=8, unique=true)
	@Size(min=8, max=8)
	private String placa;
	@Column(name="vcl_ano_fabricacao", length=4)
	private Integer anoFabricacao;
	@Column(name="vcl_ano_modelo", length=4)
	private Integer anoModelo;
	@Column(name="vcl_cor", length=20)
	private String cor;
	@ManyToOne
	@JoinColumn(name="id_modelo")
	private Modelo modelo;

	@ManyToMany
	@JoinTable(name = "rl_veiculo_acessorio",
			joinColumns = {@JoinColumn(name = "id_acessorio", referencedColumnName = "id") },
			inverseJoinColumns = {@JoinColumn(name = "id_veiculo", referencedColumnName = "id") } )
	private Set<Acessorio> acessorios;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public Set<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(Set<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}

}
