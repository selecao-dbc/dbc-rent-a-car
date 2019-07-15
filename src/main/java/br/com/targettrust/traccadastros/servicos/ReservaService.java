package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.converter.ReservaConverter;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exceptions.NegocioException;
import br.com.targettrust.traccadastros.exceptions.ObjetoNotFoundException;
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
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaConverter reservaConverter;

    public Reserva findById(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (!reserva.isPresent()) {
            throw new ObjetoNotFoundException("Reserva não encontrada.");
        }
        return reserva.get();
    }

    public Long reservarVeiculo(ReservaDto reservaDto) {
        Veiculo veiculo =
                modeloService.verificarVeiculoParaEmprestimo(
                        reservaDto.getIdModelo(),
                        reservaDto.getDataInicial(),
                        reservaDto.getDataFinal());
        Reserva reserva = reservaConverter.converter(reservaDto);
        reserva.setVeiculo(veiculo);
        reservaRepository.save(reserva);
        return reserva.getId();
    }

    public void editarReservaVeiculo(Long idReserva, ReservaDto reservaDto) {
        Reserva reserva = findById(idReserva);
        Veiculo veiculo =
                modeloService.verificarVeiculoParaEmprestimo(
                        reservaDto.getIdModelo(),
                        reservaDto.getDataInicial(),
                        reservaDto.getDataFinal());
        reserva.setDataInicial(reservaDto.getDataInicial());
        reserva.setDataFinal(reservaDto.getDataFinal());
        reserva.setVeiculo(veiculo);
        reservaRepository.save(reserva);
    }

    public void cancelar(Long id) {
        Reserva reserva = findById(id);
        if (reserva.getDataCancelamento() != null) {
            throw new NegocioException("Reserva já foi cancelada.");
        }
        reserva.setDataCancelamento(LocalDate.now());
        reservaRepository.save(reserva);
    }
}
