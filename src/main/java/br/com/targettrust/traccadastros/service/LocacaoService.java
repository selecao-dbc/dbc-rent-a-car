package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private VeiculoService veiculoService;

    public Optional<Locacao> findById(Long id) {
        return locacaoRepository.findById(id);
    }

    public Locacao save(Long id, LocacaoOuReservaDTO locacaoDTO) {
        Locacao locacao = id != null ? findById(id).orElse(null) : new Locacao();

        if (locacao != null) {
            Optional<Veiculo> veiculoOptional = veiculoService.findAvailabilityByModelo(id, null,
                    locacaoDTO.getModelo(), locacaoDTO.getDataInicial(), locacaoDTO.getDataFinal());
            if (veiculoOptional.isPresent()) {
                Veiculo veiculo = veiculoOptional.get();

                locacao.setVeiculo(veiculo);
                locacao.setDataInicial(locacaoDTO.getDataInicial());
                locacao.setDataFinal(locacaoDTO.getDataFinal());

                return locacaoRepository.save(locacao);
            }
        }
        return null;
    }

    public void deleteById(Long id) {
        locacaoRepository.deleteById(id);
    }
}
