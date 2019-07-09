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

import br.com.targettrust.traccadastros.entidades.Equipamento;
import br.com.targettrust.traccadastros.repositorio.EquipamentoRepository;

@RestController
@RequestMapping("equipamentos")
public class EquipamentoController {
	
	@Autowired
	private EquipamentoRepository equipamentoRepository;
	
	@GetMapping
	public HttpEntity<List<Equipamento>> listAll(){
		return ResponseEntity.ok(equipamentoRepository.findAll());		
	}
	
	@GetMapping("/{id}")
	public HttpEntity<Equipamento> findById(@PathVariable("id") Long id){
		Optional<Equipamento> equipamento = equipamentoRepository.findById(id);
		if(equipamento.isPresent()) {
			return ResponseEntity.ok(equipamento.get());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}		
	}

	@GetMapping("/search")
	public HttpEntity<List<Equipamento>> search(
			@RequestParam(name="id", required=false) Long id, 
			@RequestParam(name="descricao", required=false) String descricao) {
			descricao = descricao != null ? descricao.toUpperCase() : descricao;
		return ResponseEntity.ok(
				equipamentoRepository.search(id, descricao)
				);
	}
	
	@GetMapping("/desc/{descricao}")
	public HttpEntity<Equipamento> findByDescricao(@PathVariable("descricao") String descricao){
		Optional<Equipamento> equipamento = equipamentoRepository.findByDescricao(descricao.toUpperCase());
		if(equipamento.isPresent()) {
			return ResponseEntity.ok(equipamento.get());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@DeleteMapping("/{id}")
	public HttpEntity<Equipamento> deleteByID(@PathVariable("id") Long id) {
		if(equipamentoRepository.findById(id).isPresent()) {
			equipamentoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Equipamento> createEquipamento(@Valid @RequestBody Equipamento equipamento){
		if(equipamento == null || equipamento.getId() != null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(equipamentoRepository.save(equipamento));	
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Equipamento> updateEquipamento(@PathVariable("id") Long id, 
			@Valid @RequestBody Equipamento equipamento) {
		Optional<Equipamento> dbEquipamento = equipamentoRepository.findById(id);
		if(dbEquipamento.isPresent()) {
			dbEquipamento.get().setDescricao(equipamento.getDescricao());
			dbEquipamento.get().setVersion(equipamento.getVersion());	
			equipamentoRepository.save(dbEquipamento.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();		
	} 
}
