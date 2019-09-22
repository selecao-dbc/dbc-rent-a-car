package br.com.targettrust.traccadastros.repositorio;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	
	@Query("  from Veiculo veiculo "+ 
		       " where veiculo.modelo = :modelo ")
		List<Veiculo> findByModelo(
				@Param("modelo") Modelo modelo);
}
