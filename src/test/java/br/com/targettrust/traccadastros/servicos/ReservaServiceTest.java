package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.stub.VeiculoStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;


    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Test
    public void reservarVeiculo() {
        ReservaDto reserva = new ReservaDto();
        reserva.setIdModelo(1L);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        when(veiculoRepository.findByModeloId(1L)).thenReturn(VeiculoStub.gerarColecao());
        reservaService.reservarVeiculo(reserva);
    }
}
