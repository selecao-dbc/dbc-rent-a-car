package br.com.targettrust.traccadastros.converter;

import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.entidades.Locacao;
import org.springframework.stereotype.Component;

@Component
public class LocacaoConverter {

    public Locacao converter(LocacaoDto locacaoDto) {
        Locacao locacao = new Locacao();
        locacao.setDataInicial(locacaoDto.getDataInicial());
        locacao.setDataFinal(locacaoDto.getDataInicial());
        locacao.setValor(locacaoDto.getValorPago());
        return locacao;
    }
}
