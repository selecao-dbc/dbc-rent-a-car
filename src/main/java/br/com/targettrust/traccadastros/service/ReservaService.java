package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private VeiculoService veiculoService;

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva save(Long id, LocacaoOuReservaDTO reservaDTO) {
        Reserva reserva = id != null ? findById(id).orElse(null) : new Reserva();

        if (reserva != null) {
            Optional<Veiculo> veiculoOptional = veiculoService.findAvailabilityByModelo(null, id,
                    reservaDTO);
            if (veiculoOptional.isPresent()) {
                Veiculo veiculo = veiculoOptional.get();

                reserva.setVeiculo(veiculo);
                reserva.setDataInicial(reservaDTO.getDataInicial());
                reserva.setDataFinal(reservaDTO.getDataFinal());

                return reservaRepository.save(reserva);
            }
        }
        return null;
    }

    public Optional<Reserva> cancel(Long id) {
        Optional<Reserva> dbReserva = findById(id);
        if(dbReserva.isPresent()) {
            dbReserva.get().setDataCancelamento(LocalDate.now());
        }
        return dbReserva;
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }
}
