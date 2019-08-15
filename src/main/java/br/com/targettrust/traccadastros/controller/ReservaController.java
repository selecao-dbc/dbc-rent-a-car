package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.service.ReservaService;

@RestController
@RequestMapping("reservas")
public class ReservaController {

	// TODO 1 Implementar métodos para criação, alteração e cancelamento de reserva

	@Autowired
	private ReservaService reservaService;
	
	@PostMapping
	public Reserva criar(@RequestParam(required = true) Long modelo,
			@RequestParam(required = true) @DateTimeFormat LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat LocalDate dataFinal) {

		return reservaService.salvar(modelo, dataInicial, dataFinal);
	}

}
