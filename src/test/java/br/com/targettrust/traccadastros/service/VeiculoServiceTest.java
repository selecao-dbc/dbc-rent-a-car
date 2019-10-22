package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService veiculoService;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Test
    public void testFindAvailabilityByModelo() {
        Optional<Veiculo> veiculoInitOptional = Optional.of(new Carro());
        when(veiculoRepository.findAvailabilityByModelo(null, null, null, null, null))
                .thenReturn(veiculoInitOptional);

        Optional<Veiculo> veiculoOptional = veiculoService
                .findAvailabilityByModelo(null, null, new LocacaoOuReservaDTO());

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get(), is(veiculoInitOptional.get()));
        verify(veiculoRepository, times(1))
                .findAvailabilityByModelo(null, null, null, null, null);
    }
}
