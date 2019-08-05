package br.com.targettrust.traccadastros.servico;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.exception.LocacaoNaoEncontradoException;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;

public interface LocacaoService {
	
	public Locacao salvar(Locacao locacao) throws VeiculoNaoEncontradoException;

	public Locacao salvar(Modelo modelo, LocalDate dataInicial, LocalDate DataFinal, Double valorPago) throws VeiculoNaoEncontradoException;

	public Locacao alterar(Locacao locacao) throws LocacaoNaoEncontradoException;

	public List<Locacao> buscarPorVeiculo(Long id, LocalDate dataInicial, LocalDate dataFinal) throws LocacaoNaoEncontradoException;

	public List<Locacao> buscarTodos();

	public void deletar(Locacao locacao) throws LocacaoNaoEncontradoException;

	public Locacao buscarPorId(Long id) throws LocacaoNaoEncontradoException;

}
