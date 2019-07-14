package br.com.targettrust.traccadastros.servicos;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import org.springframework.stereotype.Service;

@Service
public class ReservaService {

    public Long reservarVeiculo(ReservaDto reserva) {
        return 1L; //Retorno do codigo de reserva criado
    }

}
