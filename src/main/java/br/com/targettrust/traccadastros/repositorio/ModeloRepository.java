package br.com.targettrust.traccadastros.repositorio;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.targettrust.traccadastros.entidades.Modelo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ModeloRepository extends JpaRepository<Modelo, Long>{

	@Transactional
	void deleteByNome(String nome);
	
	Modelo findByNome(String nome);

	@Query("delete Modelo modelo "
	       + " where modelo.id in( "
	       + "      select id "
		   + "         from Modelo "
		   + "         where marca.nome = :marca )")
	@Transactional
	@Modifying
    void deleteByMarca(@Param("marca") String marca);
	
	@Query("   select modelo "
			+ "  from Modelo modelo "
			+ "  join modelo.marca marca "
			+ " where (:id    is null or modelo.id = :id )"
			+ "   and (:nome  is null or modelo.nome = :nome )"
			+ "   and (:ano is null or modelo.ano = :ano )"
			+ "   and (:idMarca is null or marca.id = :idMarca)"
			+ "   and (:marca is null or UPPER(marca.nome) LIKE UPPER(concat(:marca, '%')) ) ")
	List<Modelo> search(
			@Param("id") Long id, 
			@Param("nome") String nome,
			@Param("ano") Integer ano,
			@Param("idMarca") Long idMarca, 
			@Param("marca") String marca);
}
