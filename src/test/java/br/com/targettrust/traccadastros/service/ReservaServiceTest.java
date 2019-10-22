package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReservaServiceTest {

    @InjectMocks
    private ReservaService reservaService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private VeiculoService veiculoService;

    @Test
    public void testSaveWithoutIdWithVeiculoFound() {
        Reserva reservaInit = new Reserva();
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(veiculoService.findAvailabilityByModelo(null, null, locacaoDTO))
                .thenReturn(Optional.of(new Carro()));
        when(reservaRepository.save(any())).thenReturn(reservaInit);

        Reserva reserva = reservaService.save(null, locacaoDTO);

        assertThat(reserva, is(reservaInit));
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                null, null, locacaoDTO);
        verify(reservaRepository, times(1)).save(any());
    }

    @Test
    public void testSaveWithoutIdWithVeiculoNotFound() {
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(veiculoService.findAvailabilityByModelo(null, null, locacaoDTO))
                .thenReturn(Optional.empty());

        Reserva reserva = reservaService.save(null, locacaoDTO);

        assertNull(reserva);
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                null, null, locacaoDTO);
    }

    @Test
    public void testSaveWithIdFound() {
        Reserva reservaInit = new Reserva();
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaInit));
        when(veiculoService.findAvailabilityByModelo(null, 1L, locacaoDTO))
                .thenReturn(Optional.of(new Carro()));
        when(reservaRepository.save(any())).thenReturn(reservaInit);

        Reserva reserva = reservaService.save(1L, locacaoDTO);

        assertThat(reserva, is(reservaInit));
        verify(reservaRepository, times(1)).findById(1L);
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                null, 1L, locacaoDTO);
        verify(reservaRepository, times(1)).save(any());
    }

    @Test
    public void testSaveWithIdNotFound() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        Reserva reserva = reservaService.save(1L, new LocacaoOuReservaDTO());

        assertNull(reserva);
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    public void testCancelWithIdFound() {
        Reserva reservaInit = new Reserva();

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaInit));
        when(reservaRepository.save(any())).thenReturn(reservaInit);

        Optional<Reserva> reservaOptional = reservaService.cancel(1L);

        assertTrue(reservaOptional.isPresent());
        assertThat(reservaOptional.get(), is(reservaInit));
        assertNotNull(reservaOptional.get().getDataCancelamento());
        verify(reservaRepository, times(1)).findById(1L);
        verify(reservaRepository, times(1)).save(any());
    }

    @Test
    public void testCancelWithIdNotFound() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Reserva> reservaOptional = reservaService.cancel(1L);

        assertFalse(reservaOptional.isPresent());
        verify(reservaRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteById() {
        reservaService.deleteById(1L);

        verify(reservaRepository, times(1)).deleteById(1L);
    }
}
