package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.ReservaConverter;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.exceptions.ObjetoNotFoundException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.stub.ReservaStub;
import br.com.targettrust.traccadastros.stub.VeiculoStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReservaServiceTest {

    private final Long ID_MODELO = 1L;

    private final Long ID_RESERVA = 1L;

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private VeiculoService veiculoService;

    @Mock
    private ModeloService modeloService;

    @Mock
    private ReservaRepository reservaRepository;

    @Spy
    private ReservaConverter reservaConverter;

    @Test
    public void reservarComSucesso() {
        ReservaDto reserva = ReservaStub.gerarReservaDto(ID_MODELO);
        when(modeloService.verificarVeiculoParaEmprestimo(
                reserva.getIdModelo(),
                reserva.getDataInicial(),
                reserva.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());
        reservaService.reservarVeiculo(reserva);
    }

    @Test
    public void cancelarReserva() {
        Reserva reserva = ReservaStub.gerarReserva(ID_RESERVA);
        when(reservaRepository.findById(ID_RESERVA)).thenReturn(Optional.of(reserva));
        reservaService.cancelar(ID_RESERVA);
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void cancelarReservaInexistente() {
        when(reservaRepository.findById(ID_RESERVA)).thenReturn(Optional.empty());
        reservaService.cancelar(ID_RESERVA);
    }

    @Test(expected = NegocioException.class)
    public void cancelarReservaJaCancelada() {
        Reserva reserva = ReservaStub.gerarReservaCancelamento(ID_RESERVA);
        when(reservaRepository.findById(ID_RESERVA)).thenReturn(Optional.of(reserva));
        reservaService.cancelar(ID_RESERVA);
    }

    @Test
    public void editarReserva() {
        Reserva reserva = ReservaStub.gerarReserva(ID_RESERVA);
        ReservaDto reservaDto = ReservaStub.gerarReservaDto(ID_MODELO);

        when(reservaRepository.findById(ID_RESERVA)).thenReturn(Optional.of(reserva));
        when(modeloService.verificarVeiculoParaEmprestimo(
                reservaDto.getIdModelo(),
                reservaDto.getDataInicial(),
                reservaDto.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());

        reservaService.editarReservaVeiculo(ID_RESERVA, reservaDto);
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void editarReservaInexistente() {
        Reserva reserva = ReservaStub.gerarReserva(ID_RESERVA);
        ReservaDto reservaDto = ReservaStub.gerarReservaDto(ID_MODELO);

        when(reservaRepository.findById(ID_RESERVA)).thenReturn(Optional.of(reserva));
        when(modeloService.verificarVeiculoParaEmprestimo(
                reservaDto.getIdModelo(),
                reservaDto.getDataInicial(),
                reservaDto.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());

        reservaService.editarReservaVeiculo(2L, reservaDto);
    }
}
