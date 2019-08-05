package br.com.targettrust.traccadastros.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;

/**
 * 
 * @author Waldecleber
 *
 */
@RestController
@RequestMapping("locacoes")
public class LocacaoController {
	
	@Autowired
	private LocacaoRepository locacaoRepository;
	
//	@GetMapping
//	public HttpEntity<List<Marca>> listAll() {
//		return ResponseEntity.ok(marcaRepository.findAll());		
//	}

	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Locacao> salvarLocacao(@Valid @RequestBody Locacao locacao) {
		if(locacao == null || locacao.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(locacaoRepository.save(locacao));		
	}
	
}
