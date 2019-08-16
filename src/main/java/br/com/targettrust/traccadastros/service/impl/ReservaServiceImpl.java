package br.com.targettrust.traccadastros.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.targettrust.traccadastros.config.NotFoundRuntimeException;
import br.com.targettrust.traccadastros.config.VeiculoIndisponivelRuntimeException;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.ModeloRepository;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.service.ReservaService;
import javassist.NotFoundException;

@Service
public class ReservaServiceImpl implements ReservaService{

	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private VeiculoRepository veiculoReposity;	

	@Autowired
	public ReservaServiceImpl(ReservaRepository reservaRepository, VeiculoRepository veiculoReposity) {
		super();
		this.reservaRepository = reservaRepository;
		this.veiculoReposity = veiculoReposity;
	}

	@Transactional
	@Override
	public Reserva cancelar(Long id) {
		Optional<Reserva> reserva = reservaRepository.findById(id);
		if (reserva.isPresent()) {
			reserva.get().setDataCancelamento(LocalDate.now());
			return reserva.get();
		}
		throw new NotFoundRuntimeException("Nenhuma reserva encontrada para id = "+id);
	}

	@Override
	public Reserva salvar(Long modelo, LocalDate dataInicial, LocalDate dataFinal) {
		Veiculo veiculo = getVeiculoDisponivel(modelo, dataInicial, dataFinal); 
		Reserva reserva = new Reserva();
		reserva.setDataFinal(dataFinal);
		reserva.setDataInicial(dataInicial);
		reserva.setVeiculo(veiculo);
		reserva.setVersion(1l);
		return reservaRepository.save(reserva);
	}

	private Veiculo getVeiculoDisponivel(Long modelo, LocalDate dataInicial, LocalDate dataFinal) {
		return veiculoReposity.findVeiculoDisponivel(modelo,dataInicial,dataFinal).orElseThrow(()->new VeiculoIndisponivelRuntimeException(String.format("nenhum veículo para o modelo = %s disponível", modelo)));
	}

	@Override
	public List<Reserva> findAll() {		
		return reservaRepository.findAll();
	}

	@Override
	public Reserva findbyId(Long id) {
		return reservaRepository.findById(id).orElseThrow(()->new NotFoundRuntimeException("Nenhuma reserva para id = "+id));
	}

	@Transactional
	@Override
	public Reserva alterar(Long id, Long modelo, LocalDate dataInicial, LocalDate dataFinal) {
		Optional<Reserva> reserva = reservaRepository.findById(id);
		if (reserva.isPresent()) {
			Veiculo veiculo = getVeiculoDisponivel(modelo, dataInicial, dataFinal);
			reserva.get().setVeiculo(veiculo);
			reserva.get().setDataFinal(dataFinal);
			reserva.get().setDataInicial(dataInicial);
			reserva.get().setVersion(reserva.get().getVersion()+1l);
			return reserva.get();
		}
		throw new NotFoundRuntimeException("Nenhuma reserva encontrada para id = "+id);
	}

}
