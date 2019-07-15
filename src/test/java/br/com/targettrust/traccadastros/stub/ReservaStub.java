package br.com.targettrust.traccadastros.stub;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;

import java.time.LocalDate;

public class ReservaStub {

//    public static ReservaDto gerarReservaDto() {
//        ReservaDto reserva = new ReservaDto();
//        reserva.setIdModelo(2L);
//        reserva.setDataInicial(LocalDate.now());
//        reserva.setDataFinal(LocalDate.now());
//        return reserva;
//    }

    public static ReservaDto gerarReservaDto(Long idModelo) {
        ReservaDto reserva = new ReservaDto();
        reserva.setIdModelo(idModelo);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        return reserva;
    }

    public static Reserva gerarReserva(Long id) {
        Reserva reserva = new Reserva();
        reserva.setId(id);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        return reserva;
    }

    public static Reserva gerarReservaCancelamento(Long id) {
        Reserva reserva = gerarReserva(id);
        reserva.setDataCancelamento(LocalDate.now());
        return reserva;
    }
}
