package br.com.targettrust.traccadastros.entidades.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class LocacaoOuReservaDTO {

    @NotBlank(message = "{locacaoOuReservaDTO.modelo.NotBlank.message}")
    private String modelo;

    @NotNull(message = "{locacaoOuReservaDTO.dataInicial.NotNull.message}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "{locacaoOuReservaDTO.dataInicial.FutureOrPresent.message}")
    private LocalDate dataInicial;

    @NotNull(message = "{locacaoOuReservaDTO.dataFinal.NotNull.message}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Future(message = "{locacaoOuReservaDTO.dataFinal.Future.message}")
    private LocalDate dataFinal;

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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
