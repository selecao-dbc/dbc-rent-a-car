package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.ReservaConverter;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.stub.ReservaStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;


    @Mock
    private VeiculoService veiculoService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ModeloService modeloService;

    @Spy
    private ReservaConverter reservaConverter;

    @Test
    public void reservarVeiculo() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(1L);
        when(modeloService.modeloDisponivel(1L, reserva.getDataInicial(), reserva.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(new Carro());
        reservaService.reservarVeiculo(reserva);
    }

    @Test(expected = NegocioException.class)
    public void reservarVeiculoModeloInvalido() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(1L);
        when(modeloService.modeloDisponivel(1L, reserva.getDataInicial(), reserva.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(null);
        reservaService.reservarVeiculo(reserva);
    }

    @Test(expected = NegocioException.class)
    public void reservarVeiculoIndisponivel() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(2L);
        when(modeloService.modeloDisponivel(2L, reserva.getDataInicial(), reserva.getDataFinal())).thenReturn(false);
        when(veiculoService.definirVeiculoPorModelo(2L)).thenReturn(new Carro());
        reservaService.reservarVeiculo(reserva);
    }

    @Test
    public void cancelarReservaVeiculo() {
        Reserva reserva = new Reserva();
        reserva.setId(2L);
        reserva.setDataCancelamento(LocalDate.now());
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        reservaService.cancelar(1L);
    }

    @Test(expected = NegocioException.class)
    public void cancelarReservaVeiculoInexistente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());
        reservaService.cancelar(1L);
    }

    @Test(expected = NegocioException.class)
    public void cancelarReservaVeiculoJaCancelado() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(new Reserva()));
        reservaService.cancelar(1L);
    }
}
