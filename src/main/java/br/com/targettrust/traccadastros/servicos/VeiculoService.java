package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo definirVeiculoPorModelo(Long idModelo) {
        List<Veiculo> veiculos = veiculoRepository.findByModeloId(idModelo);
        if (CollectionUtils.isEmpty(veiculos)) {
            return null;
        }
        return veiculos.get(0);
    }
}
