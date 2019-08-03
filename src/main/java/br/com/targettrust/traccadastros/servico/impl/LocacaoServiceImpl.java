package br.com.targettrust.traccadastros.servico.impl;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.repositorio.LocacaoRepository;
import br.com.targettrust.traccadastros.repositorio.MarcaRepository;
import br.com.targettrust.traccadastros.servico.LocacaoService;

public class LocacaoServiceImpl implements LocacaoService {
	
	private final LocacaoRepository locacaoRepository;
	
	private final MarcaRepository marcaRepository;
	
	public LocacaoServiceImpl(LocacaoRepository locacaoRepository, MarcaRepository marcaRepository) {
		this.locacaoRepository = locacaoRepository;
		this.marcaRepository = marcaRepository;
	}

	@Override
	public Locacao salvar(Locacao locacao) {		
		return locacaoRepository.save(locacao);		
	}

}
