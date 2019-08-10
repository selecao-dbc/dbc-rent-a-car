package br.com.targettrust.traccadastros.test;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import br.com.targettrust.traccadastros.service.ReservaService;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
public abstract class TracApplicationTest {
	
	@Autowired
	private ReservaService reservaService;
	
	@BeforeAll
	public void setup() throws Exception {
		
	}
	
	@Test
	public void salvarReserva() {
		
	}
			
	@Test
	public void atualizarReserva() {
		
	}
	
	@Test
	public void cancelarReserva() {
		
	}
}
