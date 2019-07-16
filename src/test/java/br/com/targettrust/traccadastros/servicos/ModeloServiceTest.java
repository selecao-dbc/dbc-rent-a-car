package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.stub.LocacaoStub;
import br.com.targettrust.traccadastros.stub.ReservaStub;
import br.com.targettrust.traccadastros.stub.VeiculoStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ModeloServiceTest {

    private final Long ID_MODELO = 1L;

    @InjectMocks
    private ModeloService modeloService;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private LocacaoRepository locacaoRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private VeiculoService veiculoService;

    @Test
    public void verificarVeiculoParaEmprestimoTest() {
        ReservaDto reservaFake = ReservaStub.gerarReservaDto(ID_MODELO);

        when(veiculoService.definirVeiculoPorModelo(ID_MODELO)).thenReturn(VeiculoStub.gerarCarro());
        when(veiculoRepository.findByModeloId(reservaFake.getIdModelo())).thenReturn(VeiculoStub.gerarColecao());
        when(reservaRepository.findByModeloVeiculo(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal())).thenReturn(ReservaStub.gerarReservas(1));
        when(locacaoRepository.findByIdModeloVeiculoAndDate(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal())).thenReturn(LocacaoStub.gerarLocacoes(1));
        Veiculo veiculo =
                modeloService.verificarVeiculoParaEmprestimo(
                        reservaFake.getIdModelo(),
                        reservaFake.getDataInicial(),
                        reservaFake.getDataFinal());
        assertNotNull(veiculo);
    }

    @Test(expected = NegocioException.class)
    public void verificarVeiculoParaEmprestimoIndisponivelTest() {
        ReservaDto reservaFake = ReservaStub.gerarReservaDto(ID_MODELO);

        when(veiculoService.definirVeiculoPorModelo(ID_MODELO)).thenReturn(VeiculoStub.gerarCarro());
        when(veiculoRepository.findByModeloId(reservaFake.getIdModelo())).thenReturn(VeiculoStub.gerarColecao());
        when(reservaRepository.findByModeloVeiculo(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal())).thenReturn(ReservaStub.gerarReservas(3));
        when(locacaoRepository.findByIdModeloVeiculoAndDate(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal())).thenReturn(LocacaoStub.gerarLocacoes(3));

        modeloService.verificarVeiculoParaEmprestimo(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal());
    }

    @Test(expected = NegocioException.class)
    public void verificarVeiculoParaEmprestimoInexistenteTest() {
        ReservaDto reservaFake = ReservaStub.gerarReservaDto(ID_MODELO);
        when(veiculoService.definirVeiculoPorModelo(ID_MODELO)).thenReturn(null);
        modeloService.verificarVeiculoParaEmprestimo(
                reservaFake.getIdModelo(),
                reservaFake.getDataInicial(),
                reservaFake.getDataFinal());
    }
}
