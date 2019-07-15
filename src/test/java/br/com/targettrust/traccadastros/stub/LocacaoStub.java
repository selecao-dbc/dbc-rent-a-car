package br.com.targettrust.traccadastros.stub;

import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Locacao;

import java.time.LocalDate;

public class LocacaoStub {

    public static LocacaoDto gerarLocacaoDto(Long idModelo) {
        LocacaoDto locacao = new LocacaoDto();
        locacao.setIdModelo(idModelo);
        locacao.setDataInicial(LocalDate.now());
        locacao.setDataFinal(LocalDate.now());
        return locacao;
    }

    public static Locacao gerarLocacao(Long id) {
        Locacao locacao = new Locacao();
        locacao.setId(id);
        locacao.setDataInicial(LocalDate.now());
        locacao.setDataFinal(LocalDate.now());
        return locacao;
    }
}
