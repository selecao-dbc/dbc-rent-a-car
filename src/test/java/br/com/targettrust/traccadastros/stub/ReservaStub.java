package br.com.targettrust.traccadastros.stub;

import br.com.targettrust.traccadastros.dto.ReservaDto;

import java.time.LocalDate;

public class ReservaStub {

    public static ReservaDto gerarReservaDto(){
        ReservaDto reserva = new ReservaDto();
        reserva.setIdModelo(2L);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        return reserva;
    }

    public static ReservaDto gerarReservaDto(Long id){
        ReservaDto reserva = new ReservaDto();
        reserva.setIdModelo(id);
        reserva.setDataInicial(LocalDate.now());
        reserva.setDataFinal(LocalDate.now());
        return reserva;
    }
}
