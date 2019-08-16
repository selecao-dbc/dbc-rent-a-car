package br.com.targettrust.traccadastros.repositorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

	@Query("Select distinct(veiculo) FROM Veiculo veiculo"
			+ " join veiculo.modelo as modelo on modelo.id = :modelo"
			+ " where veiculo not in ("
				+ "select reserva.veiculo From Reserva reserva "
					+ "where reserva.dataInicial between :dataInicial and :dataFinal or reserva.dataFinal between dataInicial and :dataFinal) ")
	Optional<Veiculo> findVeiculoDisponivel(@Param("modelo") Long modelo, @Param("dataInicial") LocalDate dataInicial,@Param("dataFinal") LocalDate dataFinal);
}
