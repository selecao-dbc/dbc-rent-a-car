package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.servicos.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    // TODO 1 Implementar métodos para criação, alteração e cancelamento de reserva

    @Autowired
    private ReservaService reservaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Long> criar(@Valid @RequestBody ReservaDto reserva) {
        if (reserva == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(reservaService.reservarVeiculo(reserva));
    }

}
