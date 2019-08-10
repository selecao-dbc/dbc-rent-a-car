package br.com.targettrust.traccadastros.service;

import br.com.targettrust.traccadastros.entidades.Reserva;

public interface ReservaService {
	public Reserva salvar(Reserva reserva);
	public Reserva atualizar(Reserva reserva);
	public Reserva cancelar(Reserva reserva);
}
