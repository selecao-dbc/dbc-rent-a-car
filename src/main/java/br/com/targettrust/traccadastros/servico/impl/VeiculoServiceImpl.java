package br.com.targettrust.traccadastros.servico.impl;

import java.util.Optional;

import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.VeiculoService;

public class VeiculoServiceImpl implements VeiculoService {

	private final VeiculoRepository veiculoRepository;
	
	public VeiculoServiceImpl(VeiculoRepository veiculoRepository) {
		this.veiculoRepository = veiculoRepository;
	}
	
	@Override
	public Veiculo buscarPorModelo(String modelo) throws VeiculoNaoEncontradoException {
		final Optional<Veiculo> resultado = this.veiculoRepository.findByNomeModelo(modelo);
		
        return resultado.orElseThrow(() -> 
        		new VeiculoNaoEncontradoException("Veiculo não foi encontrado"));
	}

}
