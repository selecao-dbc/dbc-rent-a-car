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
import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.impl.ReservaServiceImpl;
import br.com.targettrust.traccadastros.servico.impl.VeiculoServiceImpl;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 4 de ago de 2019
 *
 */
@Sql(value = "/sql/initial_load.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:application.properties")
public class ReservaServiceTest {
	
	private static final String MARCA_NOME_FIAT = "Fiat";
	private static final String MODELO_NOME_PALIO = "Palio 1.0";
	private static final String MODELO_NOME_FIESTA = "Fiesta 1.0";
	private static final String MODELO_NOME_F700 = "F700";
	private static final String MOTO_PLACA = "JSQ-0202";
	private static final LocalDate DATA_INICIAL = LocalDate.of(2019, 8, 6);
	private static final LocalDate DATA_FINAL = LocalDate.of(2019, 8, 16);

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private MarcaRepository marcaRepository;
		
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();	
	
	private ReservaService reservaService;
	
	private VeiculoService veiculoService;
	
	@Before
	public void setUP() throws Exception {
		reservaService = new ReservaServiceImpl(reservaRepository, veiculoRepository);
		veiculoService = new VeiculoServiceImpl(veiculoRepository);		     
	}
	
	/*
	 * ******************
	 * RESERVA DE CARRO
	 * ******************
	 */
	
	@Test
	public void reservarCarroPorModeloEData() throws Exception {
		List<Reserva> reservaList = reservaService.buscarTodos();		
		assertThat(reservaList).hasSize(2);	
		
		Veiculo carro = veiculoService.buscarPorModelo(MODELO_NOME_FIESTA);
		assertThat(carro.getModelo().getNome()).isEqualTo(MODELO_NOME_FIESTA);
		
		reservaService.salvar(carro.getModelo(), DATA_INICIAL, DATA_FINAL, 40d);
		
		reservaList = reservaService.buscarTodos();		
		assertThat(reservaList).hasSize(3);		
	}
	
	@Test(expected = VeiculoNaoEncontradoException.class)
	public void reservarCarroIndisponivel() throws Exception {		
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
		reservaService.salvar(carro.getModelo(), DATA_INICIAL, DATA_FINAL, 80d);		
	}
	
	@Test
	public void alterarReservaDeUmVeiculo()  throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo carro = optional.get();		
		List<Reserva> reservaList = reservaService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);
		
		assertThat(reservaList).hasSize(1);
		
		Reserva reserva = reservaList.get(0);
		reserva.setDataInicial(LocalDate.of(2019, 8, 14));
		reserva.setDataFinal(LocalDate.of(2019, 8, 28));
		
		reservaService.alterar(reserva);
		
		reservaList = reservaService.buscarPorVeiculo(carro.getId(), LocalDate.of(2019, 8, 14), LocalDate.of(2019, 8, 28));
		
		assertThat(reservaList.get(0).getDataInicial()).isEqualTo(LocalDate.of(2019, 8, 14));
		assertThat(reservaList.get(0).getDataFinal()).isEqualTo(LocalDate.of(2019, 8, 28));	
	}
	
	
	@Test
	public void deletarUmaReservaCarro() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo carro = optional.get();		
		List<Reserva> reservaList = reservaService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(1);
		
		reservaService.deletar(reservaList.get(0));
				
		reservaList = reservaService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(0);
	}
	
	@Test
	public void cancelarUmaReservaCarro() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo carro = optional.get();		
		List<Reserva> reservaList = reservaService.buscarPorVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(1);
		
		reservaService.cancelarReserva(reservaList.get(0));
		assertThat(reservaList.get(0).getDataCancelamento()).isToday();		
	}
	
	/*
	 * ******************
	 * RESERVA DE MOTO
	 * ******************
	 */
	
	@Test
	public void alugarMotoPorModeloEData() throws Exception {
		Veiculo moto = veiculoService.buscarPorModelo(MODELO_NOME_F700);
		assertThat(moto.getPlaca()).isEqualTo(MOTO_PLACA);
		
		reservaService.salvar(moto.getModelo(), DATA_INICIAL, DATA_FINAL, 40d);	
		List<Reserva> reservaList = reservaService.buscarTodos();
		
		assertThat(reservaList).hasSize(3);		
	}
	
	@Test
	public void deletarUmaReservaMoto() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_F700);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo moto = optional.get();		
		List<Reserva> reservaList = reservaService.buscarPorVeiculo(moto.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(1);
		
		reservaService.deletar(reservaList.get(0));
				
		reservaList = reservaService.buscarPorVeiculo(moto.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(0);
	}
	
	
	@Test
	public void cancelarUmaReservaMoto() throws Exception {
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_F700);
		assertThat(optional.isPresent()).isTrue();
		
		Veiculo moto = optional.get();		
		List<Reserva> reservaList = reservaService.buscarPorVeiculo(moto.getId(), DATA_INICIAL, DATA_FINAL);		
		assertThat(reservaList).hasSize(1);
		
		reservaService.cancelarReserva(reservaList.get(0));
		assertThat(reservaList.get(0).getDataCancelamento()).isToday();		
	}
	

}
