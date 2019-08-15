package br.com.targettrust.traccadastros.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class VeiculoTest extends TracApplicationTest{

	@Test
	public void listarVeiculos() throws Exception{
		mvc.perform(get("/veiculos")).andExpect(status().isOk());
	}
	
	@Test
	public void countVeiculos() throws Exception {
		assertThat(veiculoRepository.findAll()).isNotEmpty();
	}
}
