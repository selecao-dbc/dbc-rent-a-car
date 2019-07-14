package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.dto.ReservaDto;
import br.com.targettrust.traccadastros.servicos.LocacaoService;
import br.com.targettrust.traccadastros.servicos.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("locacao")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public HttpEntity<Long> criar(@Valid @RequestBody LocacaoDto locacaoDto) {
        if (locacaoDto == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(locacaoService.locarVeiculo(locacaoDto));
    }

}
