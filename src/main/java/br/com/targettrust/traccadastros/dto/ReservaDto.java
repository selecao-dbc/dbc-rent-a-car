package br.com.targettrust.traccadastros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ReservaDto {

    @NotNull
    @Positive
    private Long idModelo;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicial;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFinal;

    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
}
