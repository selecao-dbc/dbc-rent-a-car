package br.com.targettrust.traccadastros.repositorio;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Reserva;

public interface ReservaRepository 
	extends JpaRepository<Reserva, Long>{

	@Query("  from Reserva reserva "+ 
	       " where reserva.veiculo.placa = :placa "
	       + " and ( :dataInicial between reserva.dataInicial and reserva.dataFinal "
	       + "       OR"
	       + "       :dataFinal between reserva.dataInicial and reserva.dataFinal )")
	List<Reserva> findByPlacaVeiculo(
			@Param("placa") String placa, 
			@Param("dataInicial") LocalDate dataInicial, 
			@Param("dataFinal") LocalDate dataFinal);

	@Query("  from Reserva reserva "+ 
	       " where reserva.veiculo.id = :id "
	       + " and ( :dataInicial between reserva.dataInicial and reserva.dataFinal "
	       + "       OR"
	       + "       :dataFinal between reserva.dataInicial and reserva.dataFinal )")
	List<Reserva> findByIdVeiculo(
			@Param("id") Long id, 
			@Param("dataInicial") LocalDate dataInicial,
			@Param("dataFinal") LocalDate dataFinal);

	@Query("delete Reserva reserva "+ 
	       " where reserva.id in( "+ 
		   "        select id "+ 
	       "          from Reserva "+ 
		   "         where veiculo.placa = :placa )")
	@Transactional
	@Modifying
	void deleteByPlaca(@Param("placa") String placa);

}
