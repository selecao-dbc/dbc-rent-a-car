package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.servico.ReservaService;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 5 de ago de 2019
 */
@RestController
@RequestMapping("reservas")
public class ReservaController {
	
	@Autowired
	private ReservaService reservaService;
	
    public ReservaController(final ReservaService reservaService) {
        this.reservaService = reservaService;  
    }
    
    @GetMapping
	public HttpEntity<List<Reserva>> listAll() {
		return ResponseEntity.ok(reservaService.buscarTodos());		
	}
    
    @GetMapping("/search")
	public HttpEntity<List<Reserva>> search(
			@RequestParam(name="idVeiculo", required=false) Long idVeiculo,
			@RequestParam(name="dataInicial", required=false) 
				@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dataInicial,
			@RequestParam(name="dataFinal", required=false) 
				@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dataFinal) throws Exception {
		return ResponseEntity.ok(
				reservaService.buscarPorVeiculo(idVeiculo, dataInicial, dataFinal));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> salvarReserva(@RequestBody Reserva reserva) throws Exception {
		if(reserva == null || reserva.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		final Reserva reservaSalva = reservaService.salvar(reserva);
		return new ResponseEntity<Reserva>(reservaSalva, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> updateReserva(@PathVariable("id") Long id, 
			@Valid @RequestBody Reserva reserva) throws Exception {
		if(!id.equals(reserva.getId())) {
			ResponseEntity.badRequest().build();
		}
		Reserva dbReserva = reservaService.buscarPorId(id);
		if(dbReserva != null) {
			return ResponseEntity.ok(reservaService.salvar(reserva));
		}
		return ResponseEntity.notFound().build();		
	}
	
	@PutMapping(value="/cancelamento/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> cancelarReserva(@PathVariable("id") Long id, 
			@Valid @RequestBody Reserva reserva) throws Exception {
		if(!id.equals(reserva.getId())) {
			ResponseEntity.badRequest().build();
		}
		Reserva dbReserva = reservaService.buscarPorId(id);
		if(dbReserva != null) {
			reserva.setDataCancelamento(LocalDate.now());
			return ResponseEntity.ok(reservaService.salvar(reserva));
		}
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) throws Exception {
		reservaService.deletar(id);
	}

}
