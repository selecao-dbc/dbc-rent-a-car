package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class LocacaoServiceTest {

    @InjectMocks
    private LocacaoService locacaoService;

    @Mock
    private LocacaoRepository locacaoRepository;

    @Mock
    private VeiculoService veiculoService;

    @Test
    public void testSaveWithoutIdWithVeiculoFound() {
        Locacao locacaoInit = new Locacao();
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(veiculoService.findAvailabilityByModelo(null, null, locacaoDTO))
                .thenReturn(Optional.of(new Carro()));
        when(locacaoRepository.save(any())).thenReturn(locacaoInit);

        Locacao locacao = locacaoService.save(null, locacaoDTO);

        assertThat(locacao, is(locacaoInit));
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                null, null, locacaoDTO);
        verify(locacaoRepository, times(1)).save(any());
    }

    @Test
    public void testSaveWithoutIdWithVeiculoNotFound() {
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(veiculoService.findAvailabilityByModelo(null, null, locacaoDTO))
                .thenReturn(Optional.empty());

        Locacao locacao = locacaoService.save(null, locacaoDTO);

        assertNull(locacao);
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                null, null, locacaoDTO);
    }

    @Test
    public void testSaveWithIdFound() {
        Locacao locacaoInit = new Locacao();
        LocacaoOuReservaDTO locacaoDTO = new LocacaoOuReservaDTO();

        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacaoInit));
        when(veiculoService.findAvailabilityByModelo(1L, null, locacaoDTO))
                .thenReturn(Optional.of(new Carro()));
        when(locacaoRepository.save(any())).thenReturn(locacaoInit);

        Locacao locacao = locacaoService.save(1L, locacaoDTO);

        assertThat(locacao, is(locacaoInit));
        verify(locacaoRepository, times(1)).findById(1L);
        verify(veiculoService, times(1)).findAvailabilityByModelo(
                1L, null, locacaoDTO);
        verify(locacaoRepository, times(1)).save(any());
    }

    @Test
    public void testSaveWithIdNotFound() {
        when(locacaoRepository.findById(1L)).thenReturn(Optional.empty());

        Locacao locacao = locacaoService.save(1L, new LocacaoOuReservaDTO());

        assertNull(locacao);
        verify(locacaoRepository, times(1)).findById(1L);
    }
}
