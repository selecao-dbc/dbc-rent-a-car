package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.LocacaoConverter;
import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.exceptions.ObjetoNotFoundException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.stub.LocacaoStub;
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
public class LocacaoServiceTest {

    private final Long ID_MODELO = 1L;

    private final Long ID_LOCACAO = 1L;

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
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(ID_MODELO);
        when(modeloService.verificarVeiculoParaEmprestimo(
                locacaoDto.getIdModelo(),
                locacaoDto.getDataInicial(),
                locacaoDto.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());
        locacaoService.locarVeiculo(locacaoDto);
    }

    @Test
    public void editarLocacao() {
        Locacao locacao = LocacaoStub.gerarLocacao(ID_LOCACAO);
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(ID_MODELO);

        when(locacaoRepository.findById(ID_LOCACAO)).thenReturn(Optional.of(locacao));
        when(modeloService.verificarVeiculoParaEmprestimo(
                locacaoDto.getIdModelo(),
                locacaoDto.getDataInicial(),
                locacaoDto.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());

        locacaoService.editarLocacaoVeiculo(ID_LOCACAO, locacaoDto);
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void editarLocacaoInexistente() {
        Locacao locacao = LocacaoStub.gerarLocacao(ID_LOCACAO);
        LocacaoDto locacaoDto = LocacaoStub.gerarLocacaoDto(ID_MODELO);

        when(locacaoRepository.findById(ID_LOCACAO)).thenReturn(Optional.of(locacao));
        when(modeloService.verificarVeiculoParaEmprestimo(
                locacaoDto.getIdModelo(),
                locacaoDto.getDataInicial(),
                locacaoDto.getDataFinal())).thenReturn(VeiculoStub.gerarCarro());

        locacaoService.editarLocacaoVeiculo(2L, locacaoDto);
    }
}
