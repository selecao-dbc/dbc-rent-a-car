package br.com.targettrust.traccadastros.stub;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Moto;
import br.com.targettrust.traccadastros.entidades.Veiculo;

import java.util.Arrays;
import java.util.List;

public class VeiculoStub {

    public static List<Veiculo> gerarColecao() {
        return
                Arrays.asList(
                        gerarMoto(),
                        gerarCarro(),
                        gerarCarro(),
                        gerarMoto());
    }

    public static Moto gerarMoto() {
        Moto moto = new Moto();
        moto.setId(1L);
        moto.setModelo(new Modelo());
        moto.setPlaca("PPP-9999");
        moto.setAnoFabricacao(2010);
        moto.setCilindradas(70);
        return moto;
    }

    public static Carro gerarCarro() {
        Carro carro = new Carro();
        carro.setId(2L);
        carro.setModelo(new Modelo());
        carro.setPlaca("IPP-6666");
        carro.setAnoFabricacao(2014);
        carro.setPortas(4);
        return carro;
    }

}
