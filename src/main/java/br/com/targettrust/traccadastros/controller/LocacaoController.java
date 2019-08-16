package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.service.LocacaoService;

@RestController
@RequestMapping("locacoes")
public class LocacaoController {
	
	@Autowired
	private LocacaoService locacaoService;
	
	@PostMapping
	public Locacao criar(@RequestParam(required = true) Long modelo,
			@RequestParam(required = true) @DateTimeFormat(iso=ISO.DATE) LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat(iso=ISO.DATE) LocalDate dataFinal,
			@RequestParam(required = true) Double valor) {

		return locacaoService.salvar(modelo, dataInicial, dataFinal,valor);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Locacao> alterar(
			@PathVariable("id") Long id, 
			@RequestParam(required = true) Long modelo,
			@RequestParam(required = true) @DateTimeFormat(iso=ISO.DATE) LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat(iso=ISO.DATE) LocalDate dataFinal,
			@RequestParam(required = true) Double valor) {
		
		return ResponseEntity.ok(locacaoService.alterar(id,modelo,dataInicial,dataFinal,valor));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Locacao> cancelar(@PathVariable Long id) {	
		return 	ResponseEntity.ok(locacaoService.deletar(id));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Locacao> getByID(@PathVariable("id") Long id) {
		return ResponseEntity.ok(locacaoService.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Locacao>> findAll() {
		return ResponseEntity.ok(locacaoService.findAll());
	}

}
