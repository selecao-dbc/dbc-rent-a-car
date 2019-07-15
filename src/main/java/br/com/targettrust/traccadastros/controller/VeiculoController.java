package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public HttpEntity<List<Veiculo>> listAll() {
        return ResponseEntity.ok(veiculoRepository.findAll());
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Veiculo> createVeiculo(@Valid @RequestBody Carro veiculo) {
        if (veiculo == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(veiculoRepository.save(veiculo));
    }
}
