package br.com.targettrust.traccadastros.repositorio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Acessorio;

public interface AcessorioRepository extends JpaRepository<Acessorio, Long>{

	@Transactional
	void deleteByDescricao(String descricao);

	@Transactional
	Optional<Acessorio> findByDescricao(String descricao);

	@Query("  select e "
			+ " from Acessorio e "
			+ "where (:id is null or e.id = :id) "
			+ "  and (:descricao is null or e.descricao = :descricao )")
	List<Acessorio> search(@Param("id") Long id, 
			@Param("descricao")String descricao);

}
