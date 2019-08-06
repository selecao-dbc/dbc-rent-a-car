package br.com.targettrust.traccadastros.servico.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.ReservaNaoEncontradoException;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.ReservaRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.ReservaService;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 6 de ago de 2019
 */
@Service
public class ReservaServiceImpl implements ReservaService {

	private final ReservaRepository reservaRepository;

	private final VeiculoRepository veiculoRepository;

	public ReservaServiceImpl(ReservaRepository reservaRepository, VeiculoRepository veiculoRepository) {
		this.reservaRepository = reservaRepository;
		this.veiculoRepository = veiculoRepository;
	}

	@Override
	public Reserva salvar(Modelo modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor)
			throws VeiculoNaoEncontradoException {
		Optional<Veiculo> veiculo = veiculoRepository.findByNomeModelo(modelo.getNome());
		if (!veiculo.isPresent()) {
			throw new VeiculoNaoEncontradoException(
					"Não foi encontrado nenhum veiculo com este modelo: " + modelo.getNome());
		}

		Reserva reserva = new Reserva();
		reserva.setVeiculo(veiculo.get());
		reserva.setDataInicial(dataInicial);
		reserva.setDataFinal(dataFinal);

		return reservaRepository.save(reserva);
	}

	@Override
	public List<Reserva> buscarTodos() {
		return reservaRepository.findAll();
	}

	@Override
	public List<Reserva> buscarPorVeiculo(Long id, LocalDate dataInicial, LocalDate dataFinal) throws ReservaNaoEncontradoException {
		final List<Reserva> resultado = reservaRepository.findByIdVeiculo(id, dataInicial, dataFinal);

		if (resultado.isEmpty()) {
			new ReservaNaoEncontradoException("Não foi encontrada reserva para este veículo ");
		}
		return resultado;
	}

	@Override
	public Reserva alterar(Reserva reserva) throws ReservaNaoEncontradoException {
		if (!buscarPorVeiculo(reserva.getVeiculo().getId(), reserva.getDataInicial(), reserva.getDataFinal()).isEmpty()) {
			return reservaRepository.save(reserva);				
		} else {
			return null;
		}		
	}

	@Override
	public void deletar(Long id) throws ReservaNaoEncontradoException {
		if (reservaRepository.findById(id) != null) {
			reservaRepository.deleteById(id);				
		}		
	}

	@Override
	public void cancelarReserva(Reserva reserva) throws ReservaNaoEncontradoException {
		if (!buscarPorVeiculo(reserva.getVeiculo().getId(), reserva.getDataInicial(), reserva.getDataFinal()).isEmpty()) {
			reserva.setDataCancelamento(LocalDate.now());
			reservaRepository.save(reserva);
		}
	}

	@Override
	public Reserva salvar(Reserva reserva) throws VeiculoNaoEncontradoException {
		Optional<Veiculo> optionalVeiculo = veiculoRepository.findByModelo(reserva.getVeiculo().getModelo());
		if (!optionalVeiculo.isPresent()) {
			throw new VeiculoNaoEncontradoException("Não foi encontrado nenhum veiculo com este modelo: " + reserva.getVeiculo().getModelo());
		}
		
		return reservaRepository.save(reserva);		
	}

	@Override
	public Reserva buscarPorId(Long id) throws ReservaNaoEncontradoException {
		final Optional<Reserva> optional = reservaRepository.findById(id);
		return optional.orElseThrow(() ->
				new ReservaNaoEncontradoException("Não existe reserva com este ID"));
	}

}
