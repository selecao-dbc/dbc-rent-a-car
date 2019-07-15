package br.com.targettrust.traccadastros.dto;

import javax.validation.constraints.Positive;

public class LocacaoDto extends ReservaDto {

    @Positive
    private Double valorPago;

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }
}
