package br.com.targettrust.traccadastros.servico.impl;

import java.time.LocalDate;
import java.util.Optional;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.LocacaoService;
import br.com.targettrust.traccadastros.servico.exception.VeiculoNaoEncontradoException;

public class LocacaoServiceImpl implements LocacaoService {
	
	private final LocacaoRepository locacaoRepository;
	
	private final VeiculoRepository veiculoRepository;
	
	public LocacaoServiceImpl(LocacaoRepository locacaoRepository, VeiculoRepository veiculoRepository) {
		this.locacaoRepository = locacaoRepository;
		this.veiculoRepository = veiculoRepository;
	}

	@Override
	public Locacao salvar(Modelo modelo, LocalDate dataInicial, LocalDate DataFinal, Double valorPago) throws VeiculoNaoEncontradoException {		
		
		Optional<Veiculo> optionalVeiculo = veiculoRepository.findByNomeModelo(modelo.getNome());
		if (!optionalVeiculo.isPresent()) {
			throw new VeiculoNaoEncontradoException("Não foi encontrado nenhum veiculo com este modelo: " + modelo.getNome());
		}
		
		Locacao locacao = new Locacao();
		locacao.setDataInicial(dataInicial);
		locacao.setDataFinal(DataFinal);
		locacao.setValor(valorPago);
		locacao.setVeiculo(optionalVeiculo.get());
		
		
		return locacaoRepository.save(locacao);		
	}

}
