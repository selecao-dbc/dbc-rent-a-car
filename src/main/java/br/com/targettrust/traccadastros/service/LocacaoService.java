package br.com.targettrust.traccadastros.service;

import java.time.LocalDate;
import java.util.List;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Reserva;

public interface LocacaoService {

	Locacao deletar(Long id);

	Locacao findById(Long id);

	Locacao salvar(Long modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor);

	Locacao alterar(Long id, Long modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor);

	List<Locacao> findAll();

}
