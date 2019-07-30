package br.com.targettrust.traccadastros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;

@RestController
@RequestMapping("reservas")
public class ReservaController {

	// TODO 1 Implementar métodos para criação, alteração e cancelamento de reserva
	@Autowired
	ReservaRepository reservaRepository;

	@GetMapping
	public  List<Reserva> listaReservas(){
	   return reservaRepository.findAll();
    }
	@PostMapping( consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Reserva criarReserva(@RequestBody Reserva reserva) {
		return reservaRepository.save(reserva);
	}
	
	@PutMapping( consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public  HttpEntity<Reserva> atualizaReserva(@RequestBody Reserva reserva) {
		if(reserva!=null) {
			return ResponseEntity.ok(reservaRepository.save(reserva));
		}
		return ResponseEntity.notFound().build();		
		
	}


}
