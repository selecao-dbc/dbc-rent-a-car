package br.com.targettrust.traccadastros.entidades;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_equipamento")
@AttributeOverrides({
	@AttributeOverride(name="versao", column=@Column(name="eqp_versao"))
})
@SequenceGenerator(name = "sequence_generator", sequenceName = "sq_equipamento", allocationSize = 1)
public class Equipamento extends Entidade {
	
	@Column(name="eqp_descricao", unique=true, length=20)
	private String descricao;
	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
