package br.com.targettrust.traccadastros.servico;

import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;

public interface VeiculoService {

	public Veiculo buscarPorModelo(String string) throws VeiculoNaoEncontradoException;

}
