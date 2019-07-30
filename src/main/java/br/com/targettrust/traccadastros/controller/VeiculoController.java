package br.com.targettrust.traccadastros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.targettrust.traccadastros.entidades.Veiculo;
import br.com.targettrust.traccadastros.repositorio.VeiculoRepository;

@RestController
@RequestMapping("veiculos")
public class VeiculoController {
	
	@Autowired
	VeiculoRepository veiculoRepository;
	
	
	@GetMapping("/veiculos")
	public  List<Veiculo> listaVeiculos(){
	   return veiculoRepository.findAll();
    }
	
	

}
