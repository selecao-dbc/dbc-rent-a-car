package br.com.targettrust.traccadastros.servico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Moto;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.impl.LocacaoServiceImpl;
import br.com.targettrust.traccadastros.servico.impl.VeiculoServiceImpl;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 2 de ago de 2019
 */
@Sql(value = "/sql/initial_load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
public class LocacaoServiceTest {	

	private static final String MARCA_NOME_FIAT = "Fiat";
	private static final String MODELO_NOME_PALIO = "Palio 1.0";
	private static final String MODELO_NOME_FIESTA = "Fiesta 1.0";
	private static final String CARRO_PLACA = "JSQ-0101";
	private static final LocalDate DATA_INICIAL = LocalDate.of(2019, 8, 6);
	private static final LocalDate DATA_FINAL = LocalDate.of(2019, 8, 16);
	
	private static final String MARCA_NOME_BMW = "BMW";
	private static final String MODELO_NOME_F700 = "F700";
	private static final String MOTO_PLACA = "JSQ-0202";
	private static final LocalDate MOTO_DATA_INICIAL = LocalDate.of(2019, 8, 14);
	private static final LocalDate MOTO_DATA_FINAL = LocalDate.of(2019, 8, 23);

	@Autowired
	private LocacaoRepository locacaoRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
		
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();	 

	private LocacaoService locacaoService;
	
	private VeiculoService veiculoService;
	
	@Before
	public void setUP() throws Exception {
		locacaoService = new LocacaoServiceImpl(locacaoRepository, veiculoRepository);
		veiculoService = new VeiculoServiceImpl(veiculoRepository);		     
	}
	
	@Test
	public void pesquisarVeiculosPorMarca() {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo veiculo = optional.get();
		assertThat(veiculo.getPlaca()).isEqualTo(CARRO_PLACA);
	}
	
	
	@Test
	public void alugarCarroPorModeloEData() throws Exception {
		Veiculo carro = veiculoService.buscarPorModelo(MODELO_NOME_FIESTA);
		assertThat(carro.getModelo().getNome()).isEqualTo(MODELO_NOME_FIESTA);
		
		locacaoService.salvar(carro.getModelo(), DATA_INICIAL, DATA_FINAL, 40d);	
		List<Locacao> locacaoList = locacaoService.buscarTodos();
		
		assertThat(locacaoList).hasSize(3);
		
	}
	
	@Test(expected = VeiculoNaoEncontradoException.class)
	public void alugarCarroIndisponivel() throws Exception {		
		Marca marca = marcaRepository.findByNome(MARCA_NOME_FIAT);
		
		Modelo modelo = new Modelo();
		modelo.setMarca(marca);
		modelo.setAno(2015);
		modelo.setNome("Palio 1.8");
		modelo.setVersao("Turbo");
		
		Carro carro = new Carro();
		carro.setModelo(modelo);
		carro.setPortas(4);
		carro.setPlaca("JQX-0101");

		veiculoService.buscarPorModelo(carro.getModelo().getNome());
		locacaoService.salvar(carro.getModelo(), DATA_INICIAL, DATA_FINAL, 80d);	
		
	}
	
	@Test
	public void alterarLocacaoDeUmVeiculo() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo carro = optional.get();		
		List<Locacao> locacaoList = locacaoService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);
		
		assertThat(locacaoList).hasSize(1);
		
		Locacao locacao = locacaoList.get(0);
		locacao.setValor(20d);
		
		Locacao locacaoAlterada = locacaoService.alterar(locacao);
		assertThat(locacaoAlterada.getValor()).isEqualTo(20d);		
	}
	
	@Test
	public void deletarUmaLocacao() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo carro = optional.get();		
		List<Locacao> locacaoList = locacaoService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(locacaoList).hasSize(1);
		
		locacaoService.deletar(locacaoList.get(0).getId());
				
		locacaoList = locacaoService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(locacaoList).hasSize(0);
	}
	
	/*
	 * **************************
	 * TESTES PARA MOTO
	 * **************************
	 */
	
	@Test
	public void alugarMotoPorModeloEData() throws Exception {
		Veiculo moto = veiculoService.buscarPorModelo(MODELO_NOME_F700);
		assertThat(moto.getPlaca()).isEqualTo(MOTO_PLACA);
		
		locacaoService.salvar(moto.getModelo(), MOTO_DATA_INICIAL, MOTO_DATA_FINAL, 40d);	
		List<Locacao> locacaoList = locacaoService.buscarTodos();
		
		assertThat(locacaoList).hasSize(3);		
	}
	
	@Test(expected = VeiculoNaoEncontradoException.class)
	public void alugarMotoIndisponivel() throws Exception {		
		Marca marca = marcaRepository.findByNome(MARCA_NOME_BMW);
		
		Modelo modelo = new Modelo();
		modelo.setMarca(marca);
		modelo.setAno(2015);
		modelo.setNome("Harley Davidson");
		modelo.setVersao("Turbo");
		
		Moto moto = new Moto();
		moto.setAnoFabricacao(2018);
		moto.setCilindradas(1500);
		moto.setCor("Branco");
		moto.setModelo(modelo);
		moto.setPlaca("JQX-0101");

		veiculoService.buscarPorModelo(moto.getModelo().getNome());
		locacaoService.salvar(moto.getModelo(), MOTO_DATA_INICIAL, MOTO_DATA_FINAL, 80d);			
	}
	
	@Test
	public void alterarLocacaoDeUmaMoto()  throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_F700);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo moto = optional.get();		
		List<Locacao> locacaoList = locacaoService.buscarPorVeiculo(moto.getId(), MOTO_DATA_INICIAL, MOTO_DATA_FINAL);
		
		assertThat(locacaoList).hasSize(1);
		
		Locacao locacao = locacaoList.get(0);
		locacao.setValor(20d);
		
		Locacao locacaoAlterada = locacaoService.alterar(locacao);
		assertThat(locacaoAlterada.getValor()).isEqualTo(20d);		
	}
	
	@Test
	public void deletarUmaLocacaoMoto() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_F700);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo moto = optional.get();		
		List<Locacao> locacaoList = locacaoService.buscarPorVeiculo(moto.getId(), MOTO_DATA_INICIAL, MOTO_DATA_FINAL);		
		assertThat(locacaoList).hasSize(1);
		
		locacaoService.deletar(locacaoList.get(0).getId());
				
		locacaoList = locacaoService.buscarPorVeiculo(moto.getId(), MOTO_DATA_INICIAL, MOTO_DATA_FINAL);		
		assertThat(locacaoList).hasSize(0);
	}

}
