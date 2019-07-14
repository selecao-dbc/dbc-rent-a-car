package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public Long reservarVeiculo(ReservaDto reservaDto) {
        if (!modeloService.modeloDisponivel(reserva.getIdModelo(), reserva.getDataInicial(), reserva.getDataFinal())){
            throw new NegocioException("Modelo indisponivel para este periodo");
        }
        Veiculo veiculo = definirVeiculo(veiculoRepository.findByModeloId(reserva.getIdModelo()));
        Reserva newReserva = new Reserva();
        newReserva.setDataInicial(reservaDto.getDataInicial());
        newReserva.setDataFinal(reservaDto.getDataInicial());
        newReserva.setVeiculo(veiculo);
        reservaRepository.save(newReserva);
        return newReserva.getId();
    }

    private Veiculo definirVeiculo(List<Veiculo> veiculos) {
        if (CollectionUtils.isEmpty(veiculos)) {
            return null;
        }
        return veiculos.get(0);
    }
}
