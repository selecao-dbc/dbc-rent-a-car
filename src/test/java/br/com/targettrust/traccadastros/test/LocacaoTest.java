package br.com.targettrust.traccadastros.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.service.LocacaoService;

public class LocacaoTest extends TracApplicationTest {

	private static final LocalDate DATA_INICIAL = LocalDate.of(2018, 01, 01);
	private static final LocalDate DATA_FINAL = LocalDate.of(2018, 01, 15);	

	@Autowired
	private LocacaoService locacaoService;
	
	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/locacoes")).andExpect(status().isOk());
	}

	@Test
	public void testFindById() throws Exception {		
		Long modelo = criaCarro().getModelo().getId();
		Locacao locacao = locacaoService.salvar(modelo, DATA_INICIAL, DATA_FINAL, Double.valueOf(255));
		mvc.perform(get("/locacoes/"+locacao.getId())).andExpect(status().isOk());
	}

	@Test
	public void testFindByIdNotFound() throws Exception {		
		mvc.perform(get("/locacoes/-11")).andExpect(status().isNotFound());
	}
	
	@Test
	public void testDelete() throws Exception {
		Long modelo = criaCarro().getModelo().getId();
		Locacao locacao = locacaoService.salvar(modelo, DATA_INICIAL, DATA_FINAL, Double.valueOf(255));
		mvc.perform(delete("/locacoes/"+locacao.getId())).andExpect(status().isOk());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Long modelo = criaCarro().getModelo().getId();
		Locacao locacao = locacaoService.salvar(modelo, DATA_INICIAL, DATA_FINAL, Double.valueOf(255));
		mvc.perform(put("/locacoes/"+locacao.getId())
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.plusDays(2).toString())
				.param("dataFinal", DATA_FINAL.plusDays(1).toString())
				.param("modelo", modelo.toString())
				.param("valor", Double.valueOf(255).toString())
				).andExpect(status().isOk());			
	}

	@Test
	public void testSalvar() throws Exception {
		String modelo = criaCarro().getModelo().getId().toString();
		mvc.perform(post("/locacoes")
				.contentType("application/json")
				.param("dataInicial", DATA_INICIAL.toString())
				.param("dataFinal", DATA_FINAL.toString())
				.param("modelo", modelo)
				.param("valor", Double.valueOf(255).toString())
				).andExpect(status().isOk());	
	}
}
