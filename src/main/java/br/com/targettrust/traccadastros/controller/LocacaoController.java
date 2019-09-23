package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;

@RestController
@RequestMapping("locacoes")
public class LocacaoController {

	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private LocacaoRepository locacaoRepository;
	@Autowired
	private ModeloRepository modeloRepository;
	@Autowired
	private VeiculoRepository veiculoRepository;


	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Locacao> createLocacao(@RequestParam(required = true) String modelo,
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataInicial,
			@RequestParam(required = true) @DateTimeFormat(iso = ISO.DATE) LocalDate dataFinal){
		
		
		Veiculo veiculo = this.verificarDisponibilidadeModelo(modelo, dataInicial,  dataFinal);

		if(veiculo==null) {
			return ResponseEntity.badRequest().build();
		}
		
		Locacao locacao = new Locacao();

		locacao.setDataInicial(dataInicial);
		locacao.setDataFinal(dataFinal);
		locacao.setVeiculo(veiculo);
		
		
		return ResponseEntity.ok(locacaoRepository.save(locacao));	
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Locacao> updateLocacao(@PathVariable("id") Long id, 
			@Valid @RequestBody Locacao locacao) {
		Optional<Locacao> dbLocacao = locacaoRepository.findById(id);
		Veiculo veiculo = this.verificarDisponibilidadeModelo(locacao.getVeiculo().getModelo().getNome(), locacao.getDataInicial(),  locacao.getDataFinal());

		if(dbLocacao.isPresent()&&veiculo!=null) {
		
			dbLocacao.get().setDataInicial(locacao.getDataInicial());
			dbLocacao.get().setDataFinal(locacao.getDataFinal());
			dbLocacao.get().setVeiculo(veiculo);
			dbLocacao.get().setVersion(locacao.getVersion());
			dbLocacao.get().setValor(locacao.getValor());
			locacaoRepository.save(dbLocacao.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();		
	} 
	
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) {
		locacaoRepository.deleteById(id);
	}
	

	private Veiculo verificarDisponibilidadeModelo(String modelo, LocalDate dataInicial, LocalDate dataFinal) {
		
		Veiculo retorno = null;

		Modelo mod = modeloRepository.findByNome(modelo);

		List<Veiculo> veiculos = veiculoRepository.findByModelo(mod);
		
		for(Veiculo veiculo : veiculos){
		   if(reservaRepository.findByIdVeiculo(veiculo.getId(), dataInicial, dataFinal).isEmpty()&&locacaoRepository.findByIdVeiculo(veiculo.getId(), dataInicial, dataFinal).isEmpty()) {
			   return veiculo;
		   }
		}
		 
		return retorno;		
	}
	
}
