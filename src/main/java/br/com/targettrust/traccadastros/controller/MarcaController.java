package br.com.targettrust.traccadastros.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.targettrust.traccadastros.entidades.Marca;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;

@RestController
@RequestMapping("marcas")
public class MarcaController {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@GetMapping
	public HttpEntity<List<Marca>> listAll() {
		return ResponseEntity.ok(marcaRepository.findAll());		
	}
	
	@GetMapping("/search")
	public HttpEntity<List<Marca>> search(
			@RequestParam(name="id", required=false) Long id, 
			@RequestParam(name="nome", required=false) String nome) {
		return ResponseEntity.ok(
				marcaRepository.search(id, nome)
				);
	}
	
	@GetMapping("/{id}")
	public HttpEntity<Marca> findById(@PathVariable("id") Long id) {
		Optional<Marca> marca = marcaRepository.findById(id);
		if(marca.isPresent()) {
			return ResponseEntity.ok(marca.get());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		marcaRepository.deleteById(id);
	}

	@DeleteMapping
	public void deleteAll() {
		marcaRepository.deleteAll();
	}
	
	@PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Marca> createMarca(@Valid @RequestBody Marca marca) {
		if(marca == null || marca.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(marcaRepository.save(marca));		
	}
	
	@PostMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Marca> updateMarca(@PathVariable("id") Long id, 
			@Valid @RequestBody Marca marca) {
		if(!id.equals(marca.getId())) {
			ResponseEntity.badRequest().build();
		}
		Optional<Marca> dbMarca = marcaRepository.findById(id);
		if(dbMarca.isPresent()) {
			return ResponseEntity.ok(marcaRepository.save(marca));
		}
		return ResponseEntity.notFound().build();		
	}

}
