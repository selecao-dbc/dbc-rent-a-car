package br.com.targettrust.traccadastros.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Reserva;

public class ReservaTest extends TracApplicationTest{

	private static final LocalDate DATA_INICIAL = LocalDate.of(2018, 01, 01);
	private static final LocalDate DATA_FINAL = LocalDate.of(2018, 01, 15);
	

	@Test
	public void salvarReserva() throws Exception {		
		String modelo = criaCarro().getModelo().getId().toString();
		mvc.perform(post("/reservas")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.toString())
				.param("dataFinal", DATA_FINAL.toString())
				.param("modelo", modelo)
				).andExpect(status().isOk());		
	}
	
	@Test
	public void testVeiculoIndisponivel() throws Exception {
		
		Carro carro = criaCarro();
		reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(post("/reservas")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.plusDays(1).toString())
				.param("dataFinal", DATA_FINAL.plusDays(1).toString())
				.param("modelo", carro.getModelo().getId().toString())
				).andExpect(status().isBadRequest());
	}

	@Test
	public void testeAlterarReserva() throws Exception {
		
		Carro carro = criaCarro();
		Reserva reserva = reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(put("/reserva/"+reserva.getId())
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.plusDays(1).toString())
				.param("dataFinal", DATA_FINAL.plusDays(1).toString())
				.param("modelo", carro.getModelo().getId().toString())
				).andExpect(status().isOk());
	}
	
	@Test
	public void testGetById() throws Exception {
		Carro carro = criaCarro();
		Reserva reserva = reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(get("/reservas/"+reserva.getId())).andExpect(status().isOk());
	}
	
	@Test
	public void testCancelar() throws Exception {
		Carro carro = criaCarro();
		Reserva reserva = reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(put("/reservas/cancelar/"+reserva.getId())).andExpect(status().isOk());
	}
	
	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/reservas")).andExpect(status().isOk());
	}

}
