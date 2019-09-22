package br.com.targettrust.traccadastros.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;

@RestController
@RequestMapping("reservas")
public class ReservaController {

	// TODO 1 Implementar métodos para criação, alteração e cancelamento de reserva

	@Autowired
	private ReservaRepository reservaRepository;
	@Autowired
	private LocacaoRepository locacaoRepository;
	@Autowired
	private ModeloRepository modeloRepository;
	@Autowired
	private VeiculoRepository veiculoRepository;


	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> createReserva(@Valid @RequestBody Reserva reserva){
		
		
		Veiculo veiculo = this.verificarDisponibilidadeModelo(reserva.getVeiculo().getModelo().getNome(), reserva.getDataInicial(),  reserva.getDataFinal());

		if(reserva == null || reserva.getId() != null || veiculo==null) {
			return ResponseEntity.badRequest().build();
		}

		reserva.setVeiculo(veiculo);
		
		return ResponseEntity.ok(reservaRepository.save(reserva));	
	}
	
	@PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> updateReserva(@PathVariable("id") Long id, 
			@Valid @RequestBody Reserva reserva) {
		Optional<Reserva> dbReserva = reservaRepository.findById(id);
		Veiculo veiculo = this.verificarDisponibilidadeModelo(reserva.getVeiculo().getModelo().getNome(), reserva.getDataInicial(),  reserva.getDataFinal());

		if(dbReserva.isPresent()&&veiculo!=null) {
			
			
			
			dbReserva.get().setDataInicial(reserva.getDataInicial());
			dbReserva.get().setDataFinal(reserva.getDataFinal());
			dbReserva.get().setEquipamentos(reserva.getEquipamentos());	
			dbReserva.get().setVeiculo(veiculo);
			dbReserva.get().setVersion(reserva.getVersion());
			reservaRepository.save(dbReserva.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();		
	} 
	
	
	@PutMapping(value="/{id}/cancel", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public HttpEntity<Reserva> cancelReserva(@PathVariable("id") Long id) {
		Optional<Reserva> dbReserva = reservaRepository.findById(id);
		if(dbReserva.isPresent()) {
			LocalDate dataAtual = LocalDate.now();
			dbReserva.get().setDataCancelamento(dataAtual);
			reservaRepository.save(dbReserva.get());
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();		
	}
	

	public Veiculo verificarDisponibilidadeModelo(String modelo, LocalDate dataInicial, LocalDate dataFinal) {
		
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
