package br.com.targettrust.traccadastros.repositorio;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long>{

	@Transactional
	void deleteByDescricao(String descricao);

	Optional<Equipamento> findByDescricao(String descricao);

	@Query(	  "  from Equipamento equipamento "
			+ " where (:id is null or equipamento.id = :id )"
			+ "   and (:descricao is null or equipamento.descricao = :descricao )")
	List<Equipamento> search(
			@Param("id") Long id, 
			@Param("descricao") String descricao);
}
