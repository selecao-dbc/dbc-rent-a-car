package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoDTO;
import br.com.targettrust.traccadastros.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService locacaoService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Locacao> create(@RequestBody @Valid @NotNull LocacaoDTO locacaoDTO) {

        Locacao locacao = locacaoService.save(null, locacaoDTO);

        return locacao != null ? ok(locacao) : notFound().build();
    }

    @PutMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Locacao> update(@PathVariable("id") Long id,
                                          @RequestBody @Valid @NotNull LocacaoDTO locacaoDTO){

        Locacao locacao = locacaoService.save(id, locacaoDTO);

        return locacao != null ? ok(locacao) : notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        locacaoService.deleteById(id);
    }
}
