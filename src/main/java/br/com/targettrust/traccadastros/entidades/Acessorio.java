package br.com.targettrust.traccadastros.entidades;

import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_acessorio")
@AttributeOverrides({
		@AttributeOverride(name="versao", column=@Column(name="acs_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_acessorio", allocationSize = 1)
public class Acessorio extends Entidade {
	
	@Column(name="acs_descricao")
	private String descricao;

	@ManyToMany(mappedBy = "acessorios")
	private Set<Veiculo> veiculos;

	@ManyToMany(mappedBy = "acessorios")
	private Set<Modelo> modelos;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(Set<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public Set<Modelo> getModelos() {
		return modelos;
	}

	public void setModelos(Set<Modelo> modelos) {
		this.modelos = modelos;
	}


}
