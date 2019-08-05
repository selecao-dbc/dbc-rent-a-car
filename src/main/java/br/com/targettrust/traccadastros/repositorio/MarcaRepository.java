package br.com.targettrust.traccadastros.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{

	@Transactional
	void deleteByNome(String nome);

	Marca findByNome(String nome);
	
	@Query("   select marca "
			+ "  from Marca marca "
			+ " where (:id is null or marca.id = :id )"
			+ "   and (:nome is null or marca.nome = :nome )")
	List<Marca> search(
			@Param("id") Long id, 
			@Param("nome") String nome);
}
