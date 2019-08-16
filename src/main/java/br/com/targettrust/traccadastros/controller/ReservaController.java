package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataFinal) {

		return reservaService.salvar(modelo, dataInicial, dataFinal);
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Reserva> alterar(
			@PathVariable("id") Long id, 
			@RequestParam(required = true) Long modelo,
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataFinal) {
		return ResponseEntity.ok(reservaService.alterar(id,modelo,dataInicial,dataFinal));
	}
	
	@PutMapping(value = "cancelar/{id}")
	public ResponseEntity<Reserva> cancelar(@PathVariable Long id) {	
		return 	ResponseEntity.ok(reservaService.cancelar(id));
	}

	
	@GetMapping(value = "/{id}")
	public HttpEntity<Reserva> getByID(@PathVariable("id") Long id) {
		return ResponseEntity.ok(reservaService.findbyId(id));
	}

	
	@GetMapping	
	public ResponseEntity<List<Reserva>> findAll() {
		return ResponseEntity.ok(reservaService.findAll());
	}


}
