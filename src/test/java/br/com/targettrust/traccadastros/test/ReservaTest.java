package br.com.targettrust.traccadastros.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.mock.mockito.*;
import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.config.VeiculoIndisponivelRuntimeException;
import br.com.targettrust.traccadastros.controller.ReservaController;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.service.ReservaService;
import br.com.targettrust.traccadastros.service.impl.ReservaServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservaTest extends TracApplicationTest{

	private static final LocalDate DATA_INICIAL = LocalDate.of(2018, 1, 1);
	private static final LocalDate DATA_FINAL = LocalDate.of(2018, 1, 15);

	private static final Long MODELO = 1l;
	private static final String MODELO_NOME = "Koenigsegg";
	


	@Test
	public void salvarReserva() throws Exception {
		Reserva reserva = new Reserva();
		reserva.setDataInicial(DATA_INICIAL);
		reserva.setDataFinal(DATA_FINAL);
		reserva.setVeiculo(veiculoRepository.findAll().stream().findFirst().get());
		
		given(reservaService.salvar(MODELO, DATA_INICIAL, DATA_FINAL))
		.willReturn(reserva);
		mvc.perform(post("/reservas")
				.contentType("application/json")
				.param("datainicial", DATA_INICIAL.toString())
				.param("dataFinal", DATA_FINAL.toString())
				.param("modelo", "1")
				).andExpect(status().isOk());
	}

}
