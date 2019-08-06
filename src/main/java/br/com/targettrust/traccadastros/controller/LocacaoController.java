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

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.servico.LocacaoService;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 5 de ago de 2019
 */
@RestController
@RequestMapping("/locacoes")
public class LocacaoController {
	
	@Autowired
	private LocacaoService locacaoService;
	
    public LocacaoController(final LocacaoService locacaoService) {
        this.locacaoService = locacaoService;  
    }
		
	@GetMapping
	public HttpEntity<List<Locacao>> listAll() {
		return ResponseEntity.ok(locacaoService.buscarTodos());		
	}
	
	@GetMapping("/search")
	public HttpEntity<List<Locacao>> search(
			@RequestParam(name="idVeiculo", required=false) Long idVeiculo,
			@RequestParam(name="dataInicial", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
			@RequestParam(name="dataFinal", required=false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) throws Exception {
		return ResponseEntity.ok(
				locacaoService.buscarPorVeiculo(idVeiculo, dataInicial, dataFinal));
	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Locacao> salvarLocacao(@RequestBody Locacao locacao) throws Exception {
		if(locacao == null || locacao.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		final Locacao locacaoSalva = locacaoService.salvar(locacao);
		return new ResponseEntity<Locacao>(locacaoSalva, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Locacao> updateLocacao(@PathVariable("id") Long id, 
			@Valid @RequestBody Locacao locacao) throws Exception {
		if(!id.equals(locacao.getId())) {
			ResponseEntity.badRequest().build();
		}
		Locacao dbLocacao = locacaoService.buscarPorId(id);
		if(dbLocacao != null) {
			return ResponseEntity.ok(locacaoService.salvar(locacao));
		}
		return ResponseEntity.notFound().build();		
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) throws Exception {
		locacaoService.deletar(id);
	}
	
}
