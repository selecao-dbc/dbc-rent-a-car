package br.com.targettrust.traccadastros.repositorio;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.targettrust.traccadastros.entidades.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{

    @Query(value = "SELECT veiculo.* FROM tb_veiculo veiculo " +
           " INNER JOIN tb_modelo modelo ON modelo.id = veiculo.id_modelo " +
           " WHERE modelo.mdl_nome = :modelo AND veiculo.id NOT IN (" +
           "    SELECT veiculo.id FROM tb_veiculo veiculo " +
           "        INNER JOIN tb_modelo modelo ON modelo.id = veiculo.id_modelo " +
           "        LEFT JOIN tb_locacao locacao ON locacao.id_veiculo = veiculo.id " +
           "            AND (:locacaoId IS NULL OR (CAST (CAST(:locacaoId AS character varying) AS bigint)) != locacao.id) " +
           "            AND (locacao.dt_inicio BETWEEN :dataInicial AND :dataFinal " +
           "                OR locacao.dt_fim BETWEEN :dataInicial AND :dataFinal " +
           "                OR :dataInicial BETWEEN locacao.dt_inicio AND locacao.dt_fim " +
           "                OR :dataFinal BETWEEN locacao.dt_inicio AND locacao.dt_fim) " +
           "        LEFT JOIN tb_reserva reserva ON reserva.id_veiculo = veiculo.id " +
           "            AND (:reservaId IS NULL OR (CAST (CAST(:reservaId AS character varying) AS bigint)) != reserva.id) " +
           "            AND (reserva.dt_inicial BETWEEN :dataInicial AND :dataFinal " +
           "                OR reserva.dt_final BETWEEN :dataInicial AND :dataFinal " +
           "                OR :dataInicial BETWEEN reserva.dt_inicial AND reserva.dt_final " +
           "                OR :dataFinal BETWEEN reserva.dt_inicial AND reserva.dt_final) " +
           "        WHERE modelo.mdl_nome = :modelo " +
           "            AND (locacao.id IS NOT NULL " +
           "                OR (reserva.id IS NOT NULL AND reserva.dt_cancelamento IS NULL))) " +
           " LIMIT 1", nativeQuery = true)
    Optional<Veiculo> findAvailabilityByModelo(@Param("locacaoId") Long locacaoId,
                                               @Param("reservaId") Long reservaId,
                                               @Param("modelo") String modelo,
                                               @Param("dataInicial") LocalDate dataInicial,
                                               @Param("dataFinal") LocalDate dataFinal);
}
