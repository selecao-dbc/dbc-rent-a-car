package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.dto.LocacaoDto;
import br.com.targettrust.traccadastros.servicos.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(locacaoService.locarVeiculo(locacaoDto));
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity editar(@PathVariable Long id, @Valid @RequestBody LocacaoDto locacaoDto) {
        if (id == null || locacaoDto == null) {
            return ResponseEntity.badRequest().build();
        }
        locacaoService.editarLocacaoVeiculo(id, locacaoDto);
        return ResponseEntity.ok().build();
    }

}
