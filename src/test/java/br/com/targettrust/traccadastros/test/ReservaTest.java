package br.com.targettrust.traccadastros.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;

public class ReservaTest extends TracApplicationTest{

	private static final LocalDate DATA_INICIAL = LocalDate.now();
	private static final LocalDate DATA_FINAL = LocalDate.now();
	

	@Test
	public void salvarReserva() throws Exception {		
		mvc.perform(post("/reservas")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.toString())
				.param("dataFinal", DATA_FINAL.toString())
				.param("modelo", criaCarro().getModelo().getId().toString())
				).andExpect(status().isOk());
		System.out.println("data incial>:"+DATA_INICIAL.toString());
	}
	
	@Test
	public void salvarReservaCarroIndisponivel() throws Exception {		
		mvc.perform(post("/reservas")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.toString())
				.param("dataFinal", DATA_FINAL.toString())
				.param("modelo", "-1")
				).andExpect(status().isBadRequest());
	}
	
	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/reservas")).andExpect(status().isOk());
	}

}
