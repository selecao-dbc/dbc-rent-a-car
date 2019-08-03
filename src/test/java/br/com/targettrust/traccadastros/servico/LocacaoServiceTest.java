package br.com.targettrust.traccadastros.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.servico.impl.LocacaoServiceImpl;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 2 de ago de 2019
 */
@Sql(value = "/sql/initial_load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/sql/load_table_veiculo.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
public class LocacaoServiceTest {	

	private static final String MARCA_NOME = "Fiat";
	private static final String MODELO_NOME = "Palio 1.0";	
	private static final String MODELO_VERSAO = "ECONOMY Fire Flex 8V";
	private static final String CARRO_PLACA = "JSQ-0101";
	private static final String CARRO_COR = "Azul metalico";

	@MockBean
	private LocacaoRepository locacaoRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
		
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	 

	private LocacaoService locacaoService;
	
	private Locacao locacao;
	
	private Carro carro;
	
	private Modelo modelo;
	
	private Marca marca;
	
	@Before
	public void setUP() throws Exception {
		locacaoService = new LocacaoServiceImpl(locacaoRepository, veiculoRepository);
				
		locacao = new Locacao();
		locacao.setDataInicial(LocalDate.of(2019, 8, 6));
		locacao.setDataFinal(LocalDate.of(2019, 8, 16));
		locacao.setValor(40d);
		     
	}
	
	@Test
	public void pesquisarVeiculosPorMarca() {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME);
		assertThat(optional.isPresent()).isTrue();
		Veiculo veiculo = optional.get();
		assertThat(veiculo.getPlaca()).isEqualTo(CARRO_PLACA);
	}
	
	
	@Test
	public void realizarLocacaoDeUmCarro() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME);
		locacaoService.salvar(optional.get().getModelo(), locacao.getDataInicial(), locacao.getDataFinal(), locacao.getValor());		
		verify(locacaoRepository).save(locacao);
	}
	
	@Test
	public void alugarCarroPorModeloEData() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME);
		Veiculo veiculo = optional.get();
		locacaoService.salvar(veiculo.getModelo(), locacao.getDataInicial(), locacao.getDataFinal(), locacao.getValor());	
		verify(locacaoRepository).save(locacao);		
	}
	
	@Test
	public void alugarCarroIndisponivel() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo("Palio 1.8");
		if (optional.isPresent()) {
			Modelo modelo = optional.get().getModelo();
			locacaoService.salvar(modelo, locacao.getDataInicial(), locacao.getDataFinal(), locacao.getValor());	
			
		} else {
			expectedException.expect(VeiculoNaoEncontradoException.class);
			expectedException.expectMessage("Não existe veiculo com este modelo ");
			
		}
	}

}
