package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.ReservaConverter;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.stub.LocacaoStub;
import br.com.targettrust.traccadastros.stub.ReservaStub;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class LocacaoServiceTest {

    @InjectMocks
    private LocacaoService locacaoService;


    @Mock
    private VeiculoService veiculoService;

    @Mock
    private ModeloService modeloService;

    @Test
    public void locarVeiculo() {
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(1L);
        when(modeloService.modeloDisponivel(1L, locacaoDto.getDataInicial(), locacaoDto.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(new Carro());
        locacaoService.locarVeiculo(locacaoDto);
    }
}
