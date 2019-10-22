package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Reserva> create(@RequestBody @Valid @NotNull LocacaoOuReservaDTO reservaDTO) {

        Reserva reserva = reservaService.save(null, reservaDTO);

        return reserva != null ? ok(reserva) : notFound().build();
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Reserva> update(@PathVariable("id") Long id,
                                          @RequestBody @Valid @NotNull LocacaoOuReservaDTO reservaDTO){

        Reserva reserva = reservaService.save(id, reservaDTO);

        return reserva != null ? ok(reserva) : notFound().build();
    }

    @PutMapping(value="/{id}/cancel", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Reserva> cancel(@PathVariable("id") Long id) {
        return reservaService.cancel(id).isPresent() ? ok().build() : notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        reservaService.deleteById(id);
    }
}
