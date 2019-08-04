package br.com.targettrust.traccadastros.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Veiculo;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 3 de ago de 2019
 */
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	
	@Query("   select veiculo "
			+ "  from Veiculo veiculo "
			+ " where (veiculo.modelo.nome = :modelo )")
	Optional<Veiculo> findByNomeModelo(@Param("modelo") String modelo);
}
