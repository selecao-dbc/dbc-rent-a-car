package br.com.targettrust.traccadastros.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import br.com.targettrust.traccadastros.entidades.Acessorio;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Equipamento;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.test.TracApplicationTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LocacaoControllerTest extends TracApplicationTest {

	private static final String MARCA_NOME_FIAT = "Fiat";
	private static final String MODELO_NOME_PALIO = "Palio 1.0";
	private static final String MODELO_NOME_FIESTA = "Fiesta 1.0";
	private static final String CARRO_PLACA = "JSQ-0101";
	private static final LocalDate DATA_INICIAL = LocalDate.of(2019, 8, 6);
	private static final LocalDate DATA_FINAL = LocalDate.of(2019, 8, 16);

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private LocacaoRepository locacaoRepository;

	@Test
	public void alugarCarroPorModeloEData() {
		final Optional<Veiculo> carro = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);
		
		final Acessorio acessorio = new Acessorio();
		acessorio.setDescricao("Ar condicionado");
		
		final Equipamento equipamento = new Equipamento();
		equipamento.setDescricao("Equipamento 1");
		
		final Locacao locacao = new Locacao(carro.get(), DATA_INICIAL, DATA_FINAL, 20d);		
		locacao.getEquipamentos().add(equipamento);
		
		carro.get().getAcessorios().add(acessorio);
		
		given()
			.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(locacao)
		.when()
		.post("/locacoes")
		.then()
			.log().headers().and()
			.log().body().and()
				.statusCode(HttpStatus.CREATED.value())
				.body("veiculo.modelo.nome", equalTo(MODELO_NOME_PALIO), "veiculo.placa", equalTo(CARRO_PLACA));

	}
	
	@Test
	public void alterarLocacaoDeUmVeiculo() throws Exception {		
		Optional<Veiculo> optional = veiculoRepository.findByNomeModelo(MODELO_NOME_PALIO);		
		Veiculo carro = optional.get();		
		
		List<Locacao> locacaoList = locacaoRepository.findByIdVeiculo(carro.getId(), DATA_INICIAL, DATA_FINAL);		
		Locacao locacao = locacaoList.get(0);
		locacao.setValor(20d);
		
		given()
		.request()
			.header("Accept", ContentType.ANY)
			.header("Content-type", ContentType.JSON)
			.body(locacao)
		.when()
			.pathParam("id", locacao.getId())
		.put("/locacoes/{id}")
		.then()
			.log().headers().and()
			.log().body().and()
				.statusCode(HttpStatus.OK.value())
				.body("valor", equalTo(20.0f));
		
		
	}

}
