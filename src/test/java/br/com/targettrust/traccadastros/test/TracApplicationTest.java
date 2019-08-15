package br.com.targettrust.traccadastros.test;

import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.service.ReservaService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application.properties")
@Sql(scripts = "/sql/initial_load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public abstract class TracApplicationTest {

	protected final int ANO_2019 = 2019;

	protected final String XL = "XL";

	@Autowired
	protected MockMvc mvc;

	@Autowired
	protected ReservaService reservaService;

	@Autowired
	protected VeiculoRepository veiculoRepository;

	@Autowired
	protected ReservaRepository reservaRepository;

	@Autowired
	protected ModeloRepository modeloRepository;
	
	@Autowired
	protected MarcaRepository marcaRepository;

	@After
	public void after() {
		veiculoRepository.deleteAll();
		modeloRepository.deleteAll();
	}
	
	protected Modelo criaModelo() {
		Modelo modelo = new Modelo();
		modelo.setAno(ANO_2019);
		modelo.setNome(XL);
		modelo.setVersao(XL);
		return modeloRepository.save(modelo);
	}
}
