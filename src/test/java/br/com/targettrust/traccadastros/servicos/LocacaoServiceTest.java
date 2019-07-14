package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.LocacaoConverter;
import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.stub.LocacaoStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class LocacaoServiceTest {

    @InjectMocks
    private LocacaoService locacaoService;

    @Mock
    private VeiculoService veiculoService;

    @Mock
    private ModeloService modeloService;

    @Mock
    private LocacaoRepository locacaoRepository;

    @Spy
    private LocacaoConverter locacaoConverter;

    @Test
    public void locarVeiculo() {
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(1L);
        when(modeloService.modeloDisponivel(1L, locacaoDto.getDataInicial(), locacaoDto.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(new Carro());
        locacaoService.locarVeiculo(locacaoDto);
    }

    @Test(expected = NegocioException.class)
    public void locarVeiculoModeloInvalido() {
        LocacaoDto locacao = LocacaoStub.gerarLocacaoDto(1L);
        when(modeloService.modeloDisponivel(1L, locacao.getDataInicial(), locacao.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(null);
        locacaoService.locarVeiculo(locacao);
    }

    @Test(expected = NegocioException.class)
    public void locarVeiculoIndisponivel() {
        LocacaoDto locacao = LocacaoStub.gerarLocacaoDto(2L);
        when(modeloService.modeloDisponivel(2L, locacao.getDataInicial(), locacao.getDataFinal())).thenReturn(false);
        when(veiculoService.definirVeiculoPorModelo(2L)).thenReturn(new Carro());
        locacaoService.locarVeiculo(locacao);
    }

    @Test
    public void editarLocacao() {
        Locacao locacao = new Locacao();
        locacao.setId(1L);
        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(1L);
        when(modeloService.modeloDisponivel(1L, locacaoDto.getDataInicial(), locacaoDto.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(new Carro());
        locacaoService.editarLocacaoVeiculo(1L, locacaoDto);
    }

    @Test(expected = NegocioException.class)
    public void editarLocacaoModeloInvalido() {
        Locacao locacao = new Locacao();
        locacao.setId(1L);
        when(locacaoRepository.findById(1L)).thenReturn(Optional.of(locacao));
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(1L);
        when(modeloService.modeloDisponivel(1L, locacaoDto.getDataInicial(), locacaoDto.getDataFinal())).thenReturn(true);
        when(veiculoService.definirVeiculoPorModelo(1L)).thenReturn(null);
        locacaoService.editarLocacaoVeiculo(2L, locacaoDto);
    }

    @Test(expected = NegocioException.class)
    public void editarLocacaorVeiculoIndisponivel() {
        Locacao locacao = new Locacao();
        locacao.setId(1L);
        when(locacaoRepository.findById(2L)).thenReturn(Optional.of(locacao));
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(2L);
        when(modeloService.modeloDisponivel(2L, locacaoDto.getDataInicial(), locacaoDto.getDataFinal())).thenReturn(false);
        when(veiculoService.definirVeiculoPorModelo(2L)).thenReturn(new Carro());
        when(veiculoService.definirVeiculoPorModelo(2L)).thenReturn(new Carro());
        locacaoService.editarLocacaoVeiculo(2L, locacaoDto);
    }
}
