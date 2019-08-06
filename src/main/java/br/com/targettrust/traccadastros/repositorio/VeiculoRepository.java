package br.com.targettrust.traccadastros.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Veiculo;

/**
 * 
 * @author Waldecleber Gonçalves
 * @date 3 de ago de 2019
 */
public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	Optional<Veiculo> findByModelo(Modelo modelo);

	Veiculo findByPlaca(String placa);
	
	@Query("   select veiculo "
			+ "  from Veiculo veiculo "
			+ " where (veiculo.modelo.nome = :modelo )")
	Optional<Veiculo> findByNomeModelo(@Param("modelo") String modelo);

	@Query("   select veiculo "
			+ "  from Veiculo veiculo "
			+ "  join veiculo.modelo modelo "
			+ " where (:id    	is null or veiculo.id = :id )"
			+ "   and (:placa  	is null or veiculo.placa = :placa )"
			+ "   and (:anoFabricacao is null or veiculo.anoFabricacao = :anoFabricacao )"
			+ "   and (:anoModelo 	is null or veiculo.anoModelo = :anoModelo )"
			+ "   and (:cor 		is null or veiculo.cor = :cor )"
			+ "   and (:idModelo 	is null or modelo.id = :idModelo)")	
	List<Veiculo> search(
			@Param("id") Long id,
			@Param("placa") String placa, 
			@Param("anoFabricacao") Integer anoFabricacao, 
			@Param("anoModelo") Integer anoModelo, 
			@Param("cor") Integer cor, 
			@Param("idModelo") Integer idModelo);

}