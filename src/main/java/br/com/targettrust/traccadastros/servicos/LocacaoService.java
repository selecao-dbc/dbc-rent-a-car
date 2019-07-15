package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.LocacaoConverter;
import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exceptions.ObjetoNotFoundException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocacaoService {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private LocacaoConverter locacaoConverter;

    public Locacao findById(Long id) {
        Optional<Locacao> locacao = locacaoRepository.findById(id);
        if (!locacao.isPresent()) {
            throw new ObjetoNotFoundException("Locação não encontrada.");
        }
        return locacao.get();
    }

    public Long locarVeiculo(LocacaoDto locacaoDto) {
        Veiculo veiculo =
                modeloService.verificarVeiculoParaEmprestimo(
                        locacaoDto.getIdModelo(),
                        locacaoDto.getDataInicial(),
                        locacaoDto.getDataFinal());
        Locacao locacao = locacaoConverter.converter(locacaoDto);
        locacao.setVeiculo(veiculo);
        locacaoRepository.save(locacao);
        return locacao.getId();
    }

    public void editarLocacaoVeiculo(Long idLocacao, LocacaoDto locacaoDto) {
        Locacao locacao = findById(idLocacao);
        Veiculo veiculo =
                modeloService.verificarVeiculoParaEmprestimo(
                        locacaoDto.getIdModelo(),
                        locacaoDto.getDataInicial(),
                        locacaoDto.getDataFinal());

        locacao.setDataInicial(locacaoDto.getDataInicial());
        locacao.setDataFinal(locacaoDto.getDataFinal());
        locacao.setValor(locacaoDto.getValorPago());
        locacao.setVeiculo(veiculo);
        locacaoRepository.save(locacao);
    }
}
