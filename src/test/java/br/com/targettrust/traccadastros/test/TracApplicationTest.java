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
import org.springframework.transaction.annotation.Transactional;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Moto;
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
@Transactional
public abstract class TracApplicationTest {

	protected final String COR_BLACK = "Preto";

	protected final String CARRO_PLACA = "WBM-1989";

	protected final String MARCA_KAWASAKI = "Kawasaki";

	protected final String MODELO_NINJA = "Ninja";

	protected final String MARCA_NOME = "Koenigsegg";

	protected final String MODELO_NOME = "Regera";
	
	protected final String MODELO_VERSAO = "Evo";

	protected final int ANO_2019 = 2019;

	@Autowired
	protected MockMvc mvc;

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
		marcaRepository.deleteAll();
	}
	
	protected Marca criaMarca() {
		Marca marca = new Marca();
		marca.setNome(MARCA_NOME);
		marca.setVersion(1l);
		return marcaRepository.save(marca);
	}
	
	//https://www.koenigsegg.com/car/regera/
	protected Modelo criaModeloKoenigsegg() {
		Modelo modelo = new Modelo();
		modelo.setAno(ANO_2019);
		modelo.setNome(MODELO_NOME);		
		modelo.setVersao(MODELO_VERSAO);
		modelo.setMarca(criaMarca());
		return modeloRepository.save(modelo);
	}
	
	protected Carro criaCarro() {
		Carro carro = new Carro();
		carro.setAnoFabricacao(ANO_2019);
		carro.setCor(COR_BLACK);
		carro.setModelo(criaModeloKoenigsegg());
		carro.setPlaca(CARRO_PLACA);
		carro.setPortas(3);
		carro.setVersion(1l);
		return veiculoRepository.save(carro);
	}
	
	protected Moto criaMoto() {
		Moto moto = new Moto();
		moto.setAnoFabricacao(ANO_2019);
		moto.setCilindradas(400);
		moto.setCor(COR_BLACK);
		moto.setModelo(criaModeloMoto());
		return veiculoRepository.save(moto);
	}

	protected Modelo criaModeloMoto() {
		Modelo modelo = new Modelo();
		modelo.setAno(ANO_2019);
		modelo.setMarca(criaMarcaKawasaki());
		modelo.setNome(MODELO_NINJA);
		modelo.setVersao("400");
		return modeloRepository.save(modelo);
	}

	private Marca criaMarcaKawasaki() {
		Marca marca = new Marca();
		marca.setNome(MARCA_KAWASAKI);
		marca.setVersion(1l);
		return marcaRepository.save(marca);
	}
}
