package br.com.targettrust.traccadastros.entidades;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("MOTO")
public class Moto extends Veiculo {
	
	@Column(name="vcl_cilindradas", length=4)
	private Integer cilindradas;

	public Integer getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(Integer cilindradas) {
		this.cilindradas = cilindradas;
	}

}
