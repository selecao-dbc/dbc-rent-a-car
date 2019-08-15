package br.com.targettrust.traccadastros.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

public class TracModeloTest extends TracApplicationTest {

	@Test
	public void testFindAll() throws Exception {
		mvc.perform(get("/modelos")).andExpect(status().isOk());
	}

	@Test
	public void testSearch() throws Exception {

	}

	@Test
	public void testCreateModelo() throws Exception {

	}

	@Test
	public void testeDeleteById() throws Exception {

	}

	@Test
	public void testFindByNome() throws Exception {

	}

	@Test
	public void testGetById() throws Exception {

	}

	@Test
	public void testUpdateModelo() throws Exception {

	}
}
