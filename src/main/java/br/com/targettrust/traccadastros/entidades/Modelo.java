package br.com.targettrust.traccadastros.entidades;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_modelo")
@AttributeOverrides({
		@AttributeOverride(name="versao", column=@Column(name="mdl_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_modelo", allocationSize = 1)
public class Modelo extends Entidade{
	
	@Column(name="mdl_nome")
	private String nome;	
	@Column(name="mdl_versao")
	private String versao;
	@Column(name="mdl_ano")
	private Integer ano;
	
	@ManyToOne
	@JoinColumn(name="id_marca")
	private Marca marca;

	@ManyToMany
	@JoinTable(name = "rl_modelo_acessorio",
			joinColumns = {@JoinColumn(name = "id_acessorio", referencedColumnName = "id") },
			inverseJoinColumns = {@JoinColumn(name = "id_modelo", referencedColumnName = "id") } )
	private Set<Acessorio> acessorios;

	@ElementCollection
	@CollectionTable(name ="rl_anos_modelos")
	@Column(name="ano")
	private Set<Integer> anos;
	
	

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Set<Acessorio> getAcessorios() {
		return acessorios;
	}

	public void setAcessorios(Set<Acessorio> acessorios) {
		this.acessorios = acessorios;
	}
	public Set<Integer> getAnos() {
		return anos;
	}

	public void setAnos(Set<Integer> anos) {
		this.anos = anos;
	}

}
