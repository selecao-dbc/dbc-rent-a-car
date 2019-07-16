package br.com.targettrust.traccadastros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ErroExceptionDto {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataHota;

    private String mensagem;

    private String datalhe;

    private Integer statusCode;

    public ErroExceptionDto() {
    }

    public ErroExceptionDto(Date dataHota, String mensagem, String datalhe, Integer statusCode) {
        this.dataHota = dataHota;
        this.mensagem = mensagem;
        this.datalhe = datalhe;
        this.statusCode = statusCode;
    }

    public Date getDataHota() {
        return dataHota;
    }

    public void setDataHota(Date dataHota) {
        this.dataHota = dataHota;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDatalhe() {
        return datalhe;
    }

    public void setDatalhe(String datalhe) {
        this.datalhe = datalhe;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
