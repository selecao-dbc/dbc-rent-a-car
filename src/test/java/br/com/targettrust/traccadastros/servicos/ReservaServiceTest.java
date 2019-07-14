package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.stub.ReservaStub;
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

    @Mock
    private ModeloService modeloService;

    @Test
    public void reservarVeiculo() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(1L);
        when(modeloService.modeloDisponivel(1L, reserva.getDataInicial(), reserva.getDataFinal())).thenReturn(true);
        when(veiculoRepository.findByModeloId(1L)).thenReturn(VeiculoStub.gerarColecao());
        reservaService.reservarVeiculo(reserva);
    }

    @Test(expected = NegocioException.class)
    public void tentarReservarVeiculoIndisponivel() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(2L);
        when(modeloService.modeloDisponivel(2L, reserva.getDataInicial(), reserva.getDataFinal())).thenReturn(false);
        when(veiculoRepository.findByModeloId(2L)).thenReturn(VeiculoStub.gerarColecao());
        reservaService.reservarVeiculo(reserva);
    }
}
