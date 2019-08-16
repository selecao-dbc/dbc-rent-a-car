package br.com.targettrust.traccadastros.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.targettrust.traccadastros.config.NotFoundRuntimeException;
import br.com.targettrust.traccadastros.config.VeiculoIndisponivelRuntimeException;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import br.com.targettrust.traccadastros.service.LocacaoService;

@Service
public class LocacaoServiceImpl implements LocacaoService{

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	@Autowired
	private LocacaoRepository locacaoRespository;
	
	@Override
	public Locacao salvar(Long modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor) {
		Veiculo veiculo = getVeiculoDisponivel(modelo, dataInicial, dataFinal);
		Locacao locacao = new Locacao();
		locacao.setVeiculo(veiculo);
		locacao.setDataInicial(dataInicial);
		locacao.setDataFinal(dataFinal);
		locacao.setValor(valor);
		locacao.setVersion(1l);		
		return locacaoRespository.save(locacao);
	}


	@Transactional
	@Override
	public Locacao alterar(Long id, Long modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor) {
		Locacao locacao = findById(id);
		locacao.setVeiculo(getVeiculoDisponivel(modelo, dataInicial, dataFinal));
		locacao.setDataInicial(dataInicial);
		locacao.setDataFinal(dataFinal);
		locacao.setVersion(locacao.getVersion()+1l);
		return locacao;
	}
	private Veiculo getVeiculoDisponivel(Long modelo, LocalDate dataInicial, LocalDate dataFinal) {
		return veiculoRepository.findVeiculoDisponivel(modelo, dataInicial, dataFinal).orElseThrow(()->new VeiculoIndisponivelRuntimeException("Nenhum veiculo disponivel"));
	}

	@Transactional
	@Override
	public Locacao deletar(Long id) {
		Locacao locacao = findById(id);		
		locacaoRespository.delete(locacao);
		return locacao;
	}

	@Override
	public Locacao findById(Long id) {		
		return locacaoRespository.findById(id).orElseThrow(()->new NotFoundRuntimeException("nenhuma reserva encontrada para id = "+id));
	}


	@Override
	public List<Locacao> findAll() {		
		return locacaoRespository.findAll();
	}


}
