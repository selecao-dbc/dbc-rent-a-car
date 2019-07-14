package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.ReservaConverter;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ModeloService modeloService;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaConverter reservaConverter;

    public Long reservarVeiculo(ReservaDto reservaDto) {
        Veiculo veiculo = veiculoService.definirVeiculoPorModelo(reservaDto.getIdModelo());
        if (veiculo == null) {
            throw new NegocioException("Nenhum veiculo encontrado para este modelo");
        }
        if (!modeloService.modeloDisponivel(reservaDto.getIdModelo(), reservaDto.getDataInicial(), reservaDto.getDataFinal())) {
            throw new NegocioException("Modelo indisponivel para este periodo");
        }
        Reserva reserva = reservaConverter.converter(reservaDto);
        reserva.setVeiculo(veiculo);
        reservaRepository.save(reserva);
        return reserva.getId();
    }

    public void editarReservaVeiculo(Long idReserva, ReservaDto reservaDto) {
        Optional<Reserva> reserva = reservaRepository.findById(idReserva);
        if (!reserva.isPresent()) {
            throw new NegocioException("Reserva inexistente");
        }
        Veiculo veiculo = veiculoService.definirVeiculoPorModelo(reservaDto.getIdModelo());
        if (veiculo == null) {
            throw new NegocioException("Nenhum veiculo encontrado para este modelo");
        }
        if (!modeloService.modeloDisponivel(reservaDto.getIdModelo(), reservaDto.getDataInicial(), reservaDto.getDataFinal())) {
            throw new NegocioException("Modelo indisponivel para este periodo");
        }

        reserva.get().setDataInicial(reservaDto.getDataInicial());
        reserva.get().setDataFinal(reservaDto.getDataFinal());
        reserva.get().setVeiculo(veiculo);
        reservaRepository.save(reserva.get());
    }

    public void cancelar(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (!reserva.isPresent()) {
            throw new NegocioException("Reserva inexistente");
        }
        if (reserva.get().getDataCancelamento() == null) {
            throw new NegocioException("Reserva já cancelada");
        }
        reserva.get().setDataCancelamento(LocalDate.now());
        reservaRepository.save(reserva.get());
    }
}
