package br.com.targettrust.traccadastros.repositorio;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Locacao;

public interface LocacaoRepository 
	extends JpaRepository<Locacao, Long>{



	@Query("  from Locacao locacao "+ 
	       " where locacao.veiculo.placa = :placa "
	       + " and ( :dataInicial between locacao.dataInicial and locacao.dataFinal "
	       + "       OR"
	       + "       :dataFinal between locacao.dataInicial and locacao.dataFinal )")
	List<Locacao> findByPlacaVeiculo(
			@Param("placa") String placa, 
			@Param("dataInicial") LocalDate dataInicial, 
			@Param("dataFinal") LocalDate dataFinal);

	@Query("  from Locacao locacao "+
			" where locacao.veiculo.modelo.id = :idModelo"
			+ " and ( :dataInicial between locacao.dataInicial and locacao.dataFinal "
			+ "       OR"
			+ "       :dataFinal between locacao.dataInicial and locacao.dataFinal )")
	List<Locacao> findByIdModeloVeiculoAndDate(
			@Param("idModelo") Long idModelo,
			@Param("dataInicial") LocalDate dataInicial,
			@Param("dataFinal") LocalDate dataFinal);



	@Query("  from Locacao locacao "+ 
	       " where locacao.veiculo.id = :id "
	       + " and ( :dataInicial between locacao.dataInicial and locacao.dataFinal "
	       + "       OR"
	       + "       :dataFinal between locacao.dataInicial and locacao.dataFinal )")
	List<Locacao> findByIdVeiculo(
			@Param("id") Long id, 
			@Param("dataInicial") LocalDate dataInicial,
			@Param("dataFinal") LocalDate dataFinal);

	@Transactional
	@Modifying
	@Query(" delete from Locacao locacao " +
	       "  where locacao.id in ( "+
		   "        select id from Locacao l "+ 
	       "         where l.veiculo.placa = :placa " +
		   "       )")
	void deleteByVeiculo(@Param("placa") String placa);
	

}
