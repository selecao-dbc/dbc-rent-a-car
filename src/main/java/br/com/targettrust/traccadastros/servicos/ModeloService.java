package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    public boolean modeloDisponivel(Long idModelo, LocalDate dataInicial, LocalDate dataFinal) {
        List<Reserva> reservas = reservaRepository.findByModeloVeiculo(idModelo, dataInicial, dataFinal);
        List<Locacao> locacoes = locacaoRepository.findByIdModeloVeiculoAndDate(idModelo, dataInicial, dataFinal);
        List<Veiculo> veiculos = veiculoRepository.findByModeloId(idModelo);
        return (reservas.size() + locacoes.size()) < veiculos.size();
    }
}
