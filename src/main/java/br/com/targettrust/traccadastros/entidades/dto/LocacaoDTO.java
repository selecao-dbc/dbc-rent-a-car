package br.com.targettrust.traccadastros.entidades.dto;

import javax.validation.constraints.NotNull;

public class LocacaoDTO extends LocacaoOuReservaDTO {

    @NotNull(message = "{locacaoDTO.valor.NotNull.message}")
    private Double valor;

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
