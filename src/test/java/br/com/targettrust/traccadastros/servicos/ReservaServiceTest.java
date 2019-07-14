package br.com.targettrust.traccadastros.servicos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;

    @Test
    public void reservarVeiculo() {
        ReservaDto reserva = new ReservaDto();
        reserva.setIdModelo(1L);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        Long reservaCodigo = reservaService.reservarVeiculo(reserva);
        assertNotNull(reservaCodigo);
    }
}
