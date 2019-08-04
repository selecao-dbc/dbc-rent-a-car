package br.com.targettrust.traccadastros.servico;

import java.time.LocalDate;
import java.util.List;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.exception.ReservaNaoEncontradoException;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;

public interface ReservaService {

	public Reserva salvar(Modelo modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor) throws VeiculoNaoEncontradoException;

	public List<Reserva> buscarTodos();

	public List<Reserva> buscarPorVeiculo(Long id, LocalDate dataInicial, LocalDate dataFinal) throws ReservaNaoEncontradoException;

	public Reserva alterar(Reserva reserva) throws ReservaNaoEncontradoException;

	public void deletar(Reserva reserva) throws ReservaNaoEncontradoException;

	public void cancelarReserva(Reserva reserva) throws ReservaNaoEncontradoException;

}
