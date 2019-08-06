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

import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 6 de ago de 2019
 */
@RestController
@RequestMapping("veiculos")
public class VeiculoController {
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@GetMapping
	public HttpEntity<List<Veiculo>> listAll() {
		return ResponseEntity.ok(veiculoRepository.findAll());		
	}	
	
	@GetMapping("/search")
	public ResponseEntity<List<Veiculo>> search(
			@RequestParam(name="id", required=false) Long id,
			@RequestParam(name="placa", required=false) String placa,
			@RequestParam(name="anoFabricacao", required=false) Integer anoFabricacao,
			@RequestParam(name="anoModelo", required=false) Integer anoModelo,
			@RequestParam(name="cor", required=false) Integer cor,
			@RequestParam(name="idModelo", required=false) Integer idModelo) {
		
		return ResponseEntity.ok(
				veiculoRepository.search(id, placa, anoFabricacao, anoModelo, cor, idModelo)
				);
	}
	
	@GetMapping("/{id}")
	public HttpEntity<Veiculo> findById(@PathVariable("id") Long id) {
		Optional<Veiculo> veiculo = veiculoRepository.findById(id);
		if(veiculo.isPresent()) {
			return ResponseEntity.ok(veiculo.get());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		veiculoRepository.deleteById(id);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Veiculo> createVeiculo(@Valid @RequestBody Veiculo veiculo) {
		if(veiculo == null || veiculo.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(veiculoRepository.save(veiculo));		
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Veiculo> updateVeiculo(@PathVariable("id") Long id, 
			@Valid @RequestBody Veiculo veiculo) {
		if(!id.equals(veiculo.getId())) {
			ResponseEntity.badRequest().build();
		}
		Optional<Veiculo> dbVeiculo = veiculoRepository.findById(id);
		if(dbVeiculo.isPresent()) {
			return ResponseEntity.ok(veiculoRepository.save(veiculo));
		}
		return ResponseEntity.notFound().build();		
	}

}
