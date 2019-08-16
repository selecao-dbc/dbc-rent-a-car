package br.com.targettrust.traccadastros.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.service.ReservaService;

public class ReservaTest extends TracApplicationTest{

	private static final LocalDate DATA_INICIAL = LocalDate.now().plusDays(1);
	private static final LocalDate DATA_FINAL = LocalDate.now().plusDays(15);
	
	@Autowired
	private ReservaService reservaService;

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
		mvc.perform(put("/reservas/".concat(reserva.getId().toString()))
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.plusDays(2).toString())
				.param("dataFinal", DATA_FINAL.plusDays(1).toString())
				.param("modelo", carro.getModelo().getId().toString())
				).andExpect(status().isOk());
	}
	
	@Test
	public void testeAlterarReservaNotFound() throws Exception {		
		mvc.perform(put("/reservas/1")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.plusDays(2).toString())
				.param("dataFinal", DATA_FINAL.plusDays(1).toString())
				.param("modelo", "1")
				).andExpect(status().isNotFound());
	}
	
	@Test
	public void testGetById() throws Exception {
		Carro carro = criaCarro();
		Reserva reserva = reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(get("/reservas/".concat(reserva.getId().toString()))).andExpect(status().isOk());
	}
	
	@Test
	public void testGetByIdNotFound() throws Exception {
		mvc.perform(get("/reservas/1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void testCancelar() throws Exception {
		Carro carro = criaCarro();
		Reserva reserva = reservaService.salvar(carro.getModelo().getId(), DATA_INICIAL, DATA_FINAL);
		mvc.perform(put("/reservas/cancelar/"+reserva.getId())).andExpect(status().isOk());
	}
	
	@Test
	public void testCancelarNotFound() throws Exception {
		mvc.perform(put("/reservas/cancelar/1")).andExpect(status().isNotFound());
	}
	
	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/reservas")).andExpect(status().isOk());
	}

}
