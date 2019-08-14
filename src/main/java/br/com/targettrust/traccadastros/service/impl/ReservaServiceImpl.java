package br.com.targettrust.traccadastros.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	private ReservaRepository reservaRepository;
	private VeiculoRepository veiculoReposity;
	

	@Autowired
	public ReservaServiceImpl(ReservaRepository reservaRepository, VeiculoRepository veiculoReposity) {
		super();
		this.reservaRepository = reservaRepository;
		this.veiculoReposity = veiculoReposity;
	}

	@Override
	public Reserva atualizar(Reserva reserva) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva cancelar(Long reserva) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Reserva salvar(Long modelo, LocalDate dataInicial, LocalDate dataFinal) {
		Veiculo veiculo = veiculoReposity.findVeiculoDisponivel(modelo,dataInicial,dataFinal).orElseThrow(()->new VeiculoIndisponivelRuntimeException(String.format("nenhum veículo para o modelo = %s disponível", modelo))); 
		Reserva reserva = new Reserva();
		reserva.setDataFinal(dataFinal);
		reserva.setDataInicial(dataInicial);
		reserva.setVeiculo(veiculo);
		return reservaRepository.save(reserva);
	}


	@Override
	public List<Reserva> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reserva findbyId(Long id) {
		return reservaRepository.findById(id).orElseThrow(()->new NotFoundRuntimeException("Nenhuma reserva para id = "+id));
	}



	
}
