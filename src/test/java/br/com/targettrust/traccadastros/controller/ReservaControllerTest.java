package br.com.targettrust.traccadastros.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.test.TracApplicationTest;
import io.restassured.http.ContentType;

public class ReservaControllerTest extends TracApplicationTest {

	private static final String MARCA_NOME_FIAT = "Fiat";
	private static final String MODELO_NOME_PALIO = "Palio 1.0";
	private static final String MODELO_NOME_FIESTA = "Fiesta 1.0";
	private static final String CARRO_PLACA = "JSQ-0101";
	private static final LocalDate DATA_INICIAL = LocalDate.of(2019, 8, 6);
	private static final LocalDate DATA_FINAL = LocalDate.of(2019, 8, 16);

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Test
	public void reservarVeiculoPorModeloEData() {
		final Optional<Veiculo> carro = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		
		Reserva reserva = new Reserva();
		reserva.setDataInicial(DATA_INICIAL);
		reserva.setDataFinal(DATA_FINAL);
		reserva.setVeiculo(carro.get());
				
		given()
		.request()
		.header("Accept", ContentType.ANY)
		.header("Content-type", ContentType.JSON)
		.body(reserva)
	.when()
	.post("/reservas")
	.then()
		.log().headers().and()
		.log().body().and()
			.statusCode(HttpStatus.CREATED.value())
			.body("veiculo.modelo.nome", equalTo(MODELO_NOME_PALIO), "veiculo.placa", equalTo(CARRO_PLACA));

		
	}

	
	@Test
	public void alterarDataDeUmaReserva() {
		Optional<Reserva> reservaList = reservaRepository.findById(1L);		
		Reserva reserva = reservaList.get();
		reserva.setDataInicial(LocalDate.of(2019, 9, 1));
		reserva.setDataFinal(LocalDate.of(2019, 9, 10));
		
		given()
		.request()
		.header("Accept", ContentType.ANY)
		.header("Content-type", ContentType.JSON)
		.body(reserva)
	.when()
	.put("/reservas")
	.then()
		.log().headers().and()
		.log().body().and()
			.statusCode(HttpStatus.CREATED.value())
			.body("dataInicial", equalTo(LocalDate.of(2019, 9, 1)), "dataFinal", equalTo(LocalDate.of(2019, 9, 10)));

	}
	
	
	@Test
	public void deletarUmaReserva() {
		
		Optional<Reserva> reservaList = reservaRepository.findById(1L);		
		Reserva reserva = reservaList.get();
		
		given()
		.request()
		.header("Accept", ContentType.ANY)
		.header("Content-type", ContentType.JSON)
		.body(reserva)
	.when()
	.delete("/reservas")
	.then()
		.log().headers().and()
		.log().body().and()
			.statusCode(HttpStatus.OK.value());

	}
}
