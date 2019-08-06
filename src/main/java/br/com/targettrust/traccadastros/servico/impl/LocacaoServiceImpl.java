package br.com.targettrust.traccadastros.servico.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.exception.LocacaoNaoEncontradoException;
import br.com.targettrust.traccadastros.exception.VeiculoNaoEncontradoException;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.servico.LocacaoService;

@Service
public class LocacaoServiceImpl implements LocacaoService {
	
	private final LocacaoRepository locacaoRepository;
	
	private final VeiculoRepository veiculoRepository;
	
	public LocacaoServiceImpl(LocacaoRepository locacaoRepository, VeiculoRepository veiculoRepository) {
		this.locacaoRepository = locacaoRepository;
		this.veiculoRepository = veiculoRepository;
	}
	
	@Override
	public Locacao salvar(Locacao locacao) throws VeiculoNaoEncontradoException {	
		Optional<Veiculo> optionalVeiculo = veiculoRepository.findByModelo(locacao.getVeiculo().getModelo());
		if (!optionalVeiculo.isPresent()) {
			throw new VeiculoNaoEncontradoException("Não foi encontrado nenhum veiculo com este modelo: " + locacao.getVeiculo().getModelo());
		}
		
		return locacaoRepository.save(locacao);		
	}

	@Override
	public Locacao salvar(Modelo modelo, LocalDate dataInicial, LocalDate dataFinal, Double valorPago) throws VeiculoNaoEncontradoException {	
		Optional<Veiculo> optionalVeiculo = veiculoRepository.findByNomeModelo(modelo.getNome());
		if (!optionalVeiculo.isPresent()) {
			throw new VeiculoNaoEncontradoException("Não foi encontrado nenhum veiculo com este modelo: " + modelo.getNome());
		}
		
		Locacao locacao = new Locacao(optionalVeiculo.get(), dataInicial, dataFinal, valorPago);
		
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
	public void deletar(Long id) throws LocacaoNaoEncontradoException {
		if (locacaoRepository.findById(id) != null) {
			locacaoRepository.deleteById(id);				
		}		
	}

	@Override
	public Locacao buscarPorId(Long id) throws LocacaoNaoEncontradoException {		
		final Optional<Locacao> optional = locacaoRepository.findById(id);
		return optional.orElseThrow(() ->
				new LocacaoNaoEncontradoException("Não existe Locação com este ID"));
	}

}
