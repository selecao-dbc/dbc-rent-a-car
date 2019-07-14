package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.servicos.ReservaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Cria uma nova reserva.",
            notes = "Cria um novo resgistro de reserva de veciulos."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Long.class)
    })
    public ResponseEntity criar(@Valid @RequestBody ReservaDto reserva) {
        if (reserva == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reservaService.reservarVeiculo(reserva));
    }

    @PostMapping(path = "/{id}/cancelar", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Cancela uma reserva.",
            notes = "Executa o cancelamento de uma reserva ainda não cancelada."
    )
    public ResponseEntity cancelar(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        reservaService.cancelar(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "Edita uma reserva.",
            notes = "Edita um reserva previamente já cadastrada."
    )
    public ResponseEntity editar(@PathVariable Long id, @Valid @RequestBody ReservaDto reserva) {
        if (id == null || reserva == null) {
            return ResponseEntity.badRequest().build();
        }
        reservaService.editarReservaVeiculo(id, reserva);
        return ResponseEntity.ok().build();
    }
}
