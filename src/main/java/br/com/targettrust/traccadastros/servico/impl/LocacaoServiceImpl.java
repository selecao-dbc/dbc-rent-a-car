package br.com.targettrust.traccadastros.servico.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.LocacaoNaoEncontradoException;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.LocacaoService;

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

	@Override
	public Locacao alterar(Locacao locacao)
			throws LocacaoNaoEncontradoException {
		
		if (!buscarPorVeiculo(locacao.getVeiculo().getId(), locacao.getDataInicial(), locacao.getDataFinal()).isEmpty()) {
			return locacaoRepository.save(locacao);			
		} else {
			return null;
		}
		
	}

	@Override
	public List<Locacao> buscarPorVeiculo(Long id, LocalDate dataInicial, LocalDate dataFinal) throws LocacaoNaoEncontradoException {		
		final List<Locacao> resultado = locacaoRepository.findByIdVeiculo(id, dataInicial, dataFinal);
		
		if (resultado.isEmpty()) {
			new LocacaoNaoEncontradoException("Não foi encontrada locaçao para este veículo ");			
		}
        return resultado;
	}

	@Override
	public List<Locacao> buscarTodos() {
		return locacaoRepository.findAll();
	}

	@Override
	public void deletar(Locacao locacao) throws LocacaoNaoEncontradoException {
		if (!buscarPorVeiculo(locacao.getVeiculo().getId(), locacao.getDataInicial(), locacao.getDataFinal()).isEmpty()) {
			locacaoRepository.deleteByVeiculo(locacao.getVeiculo().getPlaca());				
		}		
	}

}
