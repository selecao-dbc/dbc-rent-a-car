package br.com.targettrust.traccadastros.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class MarcaTest extends TracApplicationTest {

	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/marcas")).andExpect(status().isOk());
	}

	@Test
	public void listaMarcaRepository() throws Exception {
		assertThat(marcaRepository.findAll()).isNotEmpty();
	}

	@Test
	public void testCreate() throws Exception {

	}

	@Test
	public void deleteAll() throws Exception {

	}

	@Test
	public void testeDeleteById() throws Exception {

	}

	@Test
	public void testFindById() throws Exception {

	}

	@Test
	public void testSearch() throws Exception {

	}

	@Test
	public void testUpdate() throws Exception {

	}
}
