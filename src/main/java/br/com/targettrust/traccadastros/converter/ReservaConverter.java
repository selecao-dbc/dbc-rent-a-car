package br.com.targettrust.traccadastros.converter;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.entidades.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaConverter {

    public Reserva converter(ReservaDto reservaDto){
        Reserva reserva = new Reserva();
        reserva.setDataInicial(reservaDto.getDataInicial());
        reserva.setDataFinal(reservaDto.getDataInicial());
        return reserva;
    }
}
