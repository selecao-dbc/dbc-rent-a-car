package br.com.targettrust.traccadastros.test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.config.VeiculoIndisponivelRuntimeException;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.service.ReservaService;
import br.com.targettrust.traccadastros.service.impl.ReservaServiceImpl;


@Transactional
@SpringBootTest(classes = TracCadastrosApplication.class)
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
public class TracApplicationReservaTest {
	
	private static final LocalDate DATA_INICIAL = LocalDate.of(2018, 1, 1);
	private static final LocalDate DATA_FINAL = LocalDate.of(2018, 1, 15);
	private static final Double VALOR = 55.99;
	private static final Long MODELO = 1l;
	private static final String MODELO_NOME = "Koenigsegg";
		
	
	private ReservaService reservaService;
	
	@MockBean
	private VeiculoRepository veiculoRepository;
	
	@MockBean
	private ReservaRepository reservaRepository;
	
	@MockBean
	private ModeloRepository modeloRepository;
	
	@Before
	public void setup() {
		reservaService = new ReservaServiceImpl(reservaRepository,veiculoRepository);
	}
	@After
	public void after() {
		veiculoRepository.deleteAll();
	}
	@Test(expected = VeiculoIndisponivelRuntimeException.class)
	public void salvarReserva() {
		Reserva reserva = reservaService.salvar(modelo.getId(), DATA_INICIAL, DATA_FINAL);		
		verify(veiculoRepository).findVeiculoDisponivel(MODELO, DATA_INICIAL, DATA_INICIAL);
		verify(reservaRepository).save(reserva);
	}
	
	@Test
	public void verificaDisponibilidadeVeiculo(){
		Carro veiculo = geraVeiculo("JXT-1875", geraModelo());
		assertThat(veiculoRepository.findVeiculoDisponivel(1l,DATA_INICIAL,DATA_FINAL)).isPresent();
	}
	@Test
	public void listVeiculos() {
		geraVeiculo("jtx-8989", geraModelo());
		List<Veiculo> list = veiculoRepository.findAll();
		assertThat(list).isNotEmpty();
	}
			
	@Test
	public void atualizarReserva() {
		
	}
	
	@Test
	public void cancelarReserva() {
		
	}
	
	@Test
	public void salvaVeiculo() {		
		veiculoRepository.save(geraVeiculo("XXX-8888", geraModelo()));
	}
	
	Carro geraVeiculo(String placa,Modelo modelo){
		Carro veiculo = new Carro();
		veiculo.setPlaca(placa);
		veiculo.setModelo(modelo);
		veiculo.setPortas(5);
		return veiculoRepository.save(veiculo);		
	}
	
	Modelo geraModelo(){
		Modelo modelo = new Modelo();
		modelo.setId(1l);
		modelo.setNome(MODELO_NOME);
		return modeloRepository.save(modelo);
	}
}
