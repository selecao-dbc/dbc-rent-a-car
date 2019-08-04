package br.com.targettrust.traccadastros.entidades;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@DiscriminatorValue("CARRO")
public class Carro extends Veiculo {
	
	@NotNull
	@Positive
	@Column(name="vcl_portas", length=1)
	private Integer portas;

	public Integer getPortas() {
		return portas;
	}

	public void setPortas(Integer portas) {
		this.portas = portas;
	}
}
