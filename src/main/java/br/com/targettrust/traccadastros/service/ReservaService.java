package br.com.targettrust.traccadastros.service;

import java.time.LocalDate;
import java.util.List;

import br.com.targettrust.traccadastros.entidades.Reserva;

public interface ReservaService {
	public Reserva salvar(Long modeloId, LocalDate dataInicial, LocalDate dataFinal);
	public Reserva cancelar(Long reserva);
	public List<Reserva> findAll();
	public Reserva findbyId(Long id);
	public Reserva alterar(Long id, Long modelo, LocalDate dataInicial, LocalDate dataFinal);
}
