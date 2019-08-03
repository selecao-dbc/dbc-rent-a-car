package br.com.targettrust.traccadastros.servico;

import java.time.LocalDate;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.servico.exception.VeiculoNaoEncontradoException;

public interface LocacaoService {

	Locacao salvar(Modelo modelo, LocalDate dataInicial, LocalDate DataFinal, Double valorPago) throws VeiculoNaoEncontradoException;

}
