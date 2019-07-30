package br.com.targettrust.traccadastros.repositoriotest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestReservaRepository {
	
	@Autowired
	ReservaRepository reservaRepository;
	
	@Test
	public void salvarReservaTest() {
	
		
	}
	

}
