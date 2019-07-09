package br.com.targettrust.traccadastros.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tb_marca")
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_marca", allocationSize = 1)
@AttributeOverrides({
		@AttributeOverride(name="versao", column=@Column(name="mar_versao"))
})
public class Marca extends Entidade {
	
	@Column(name="mar_nome")
	@Size(min=3, max=20)
	@NotNull
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

}
