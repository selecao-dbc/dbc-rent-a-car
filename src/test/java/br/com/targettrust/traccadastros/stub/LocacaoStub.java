package br.com.targettrust.traccadastros.stub;

import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Locacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Locacao> gerarLocacoes(int qtd) {
        List<Locacao> locacaos = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            Locacao locacao = new Locacao();
            locacao.setId((long) i);
            locacao.setDataInicial(LocalDate.now());
            locacao.setDataFinal(LocalDate.now());
            locacaos.add(locacao);
        }
        return locacaos;
    }
}
