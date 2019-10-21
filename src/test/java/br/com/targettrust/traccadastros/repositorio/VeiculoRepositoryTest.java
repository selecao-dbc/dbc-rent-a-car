package br.com.targettrust.traccadastros.repositorio;

import br.com.targettrust.traccadastros.entidades.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private LocacaoRepository locacaoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Before
    public void setup() {
        if (modeloRepository.findAll().isEmpty()) {
            Modelo modeloPrisma = modeloRepository.save(createModelo(2014, "Prisma"));
            Veiculo veiculoPrismaAzul = veiculoRepository.save(createVeiculo(2017, "Azul", "PMO-2233",
                    4, modeloPrisma));
            locacaoRepository.save(
                    createLocacao(
                            LocalDate.of(2020, 6, 12),
                            LocalDate.of(2020, 6, 15),
                            120D, veiculoPrismaAzul));
            reservaRepository.save(
                    createReserva(
                            LocalDate.of(2020, 6, 16),
                            LocalDate.of(2020, 6, 18),
                            veiculoPrismaAzul));
            locacaoRepository.save(
                    createLocacao(
                            LocalDate.of(2020, 6, 19),
                            LocalDate.of(2020, 6, 20),
                            120D, veiculoPrismaAzul));
            reservaRepository.save(
                    createReserva(
                            LocalDate.of(2020, 6, 21),
                            LocalDate.of(2020, 6, 22),
                            veiculoPrismaAzul));

            Veiculo veiculoPrismaBranco = veiculoRepository.save(createVeiculo(2017, "Branco",
                    "PMO-2234", 4, modeloPrisma));
            reservaRepository.save(
                    createReserva(
                            LocalDate.of(2020, 6, 9),
                            LocalDate.of(2020, 6, 10),
                            veiculoPrismaBranco));

            Modelo modeloSandero = modeloRepository.save(createModelo(2014, "Sandero"));
            Veiculo veiculoSanderoPrata = veiculoRepository.save(createVeiculo(2017, "Prata",
                    "PMO-2235", 4, modeloSandero));
            locacaoRepository.save(
                    createLocacao(
                            LocalDate.of(2020, 6, 14),
                            LocalDate.of(2020, 6, 16),
                            110D, veiculoSanderoPrata));

            Modelo modeloPalio = modeloRepository.save(createModelo(2014, "Palio"));
            veiculoRepository.save(createVeiculo(2017, "Verde", "PMO-2236", 4, modeloPalio));
        }
    }

    @Test
    public void testFindDisponibilityByModeloWithoutLocacaoIdAndReservaId() {

        Optional<Veiculo> veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 11));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Azul"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 11),
                LocalDate.of(2020, 6, 13));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 17),
                LocalDate.of(2020, 6, 19));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 16),
                LocalDate.of(2020, 6, 18));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 11),
                LocalDate.of(2020, 6, 23));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 15));

        assertFalse(veiculoOptional.isPresent());

        List<Reserva> reservas = reservaRepository.findByPlacaVeiculo("PMO-2234",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 10));
        if (!reservas.isEmpty()) {
            Reserva reserva = reservas.get(0);
            reserva.setDataCancelamento(LocalDate.now());
            reservaRepository.save(reserva);
        }
        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Prisma",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 15));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, null,
                "Palio",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 15));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Verde"));
    }

    @Test
    public void testFindDisponibilityByModeloUsingOnlyLocacaoId() {

        Optional<Veiculo> veiculoOptional = veiculoRepository.findAvailabilityByModelo(1L, null,
                "Prisma",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 11));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Azul"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(1L, null,
                "Prisma",
                LocalDate.of(2020, 6, 11),
                LocalDate.of(2020, 6, 13));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Azul"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(1L, null,
                "Prisma",
                LocalDate.of(2020, 6, 17),
                LocalDate.of(2020, 6, 19));
        Optional<Locacao> locacaoOptional = locacaoRepository.findById(1L);

        assertTrue(locacaoOptional.isPresent());
        assertThat(locacaoOptional.get().getVeiculo().getCor(), is("Azul"));
        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(1L, null,
                "Prisma",
                LocalDate.of(2020, 6, 16),
                LocalDate.of(2020, 6, 18));
        locacaoOptional = locacaoRepository.findById(1L);

        assertTrue(locacaoOptional.isPresent());
        assertThat(locacaoOptional.get().getVeiculo().getCor(), is("Azul"));
        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(3L, null,
                "Palio",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 15));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Verde"));
    }

    @Test
    public void testFindDisponibilityByModeloUsingOnlyReservaId() {

        Optional<Veiculo> veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, 1L,
                "Prisma",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 11));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Azul"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, 1L,
                "Prisma",
                LocalDate.of(2020, 6, 16),
                LocalDate.of(2020, 6, 18));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Azul"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, 1L,
                "Prisma",
                LocalDate.of(2020, 6, 13),
                LocalDate.of(2020, 6, 14));
        Optional<Reserva> reservaOptional = reservaRepository.findById(1L);

        assertTrue(reservaOptional.isPresent());
        assertThat(reservaOptional.get().getVeiculo().getCor(), is("Azul"));
        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, 1L,
                "Prisma",
                LocalDate.of(2020, 6, 17),
                LocalDate.of(2020, 6, 19));
        reservaOptional = reservaRepository.findById(2L);

        assertTrue(reservaOptional.isPresent());
        assertThat(reservaOptional.get().getVeiculo().getCor(), is("Azul"));
        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Branco"));

        veiculoOptional = veiculoRepository.findAvailabilityByModelo(null, 3L,
                "Palio",
                LocalDate.of(2020, 6, 9),
                LocalDate.of(2020, 6, 15));

        assertTrue(veiculoOptional.isPresent());
        assertThat(veiculoOptional.get().getCor(), is("Verde"));
    }

    private Modelo createModelo(Integer ano, String nome) {
        Modelo modelo = new Modelo();
        modelo.setAno(ano);
        modelo.setNome(nome);

        return modelo;
    }

    private Carro createVeiculo(Integer ano, String cor, String placa, Integer portas, Modelo modelo) {
        Carro carro = new Carro();
        carro.setAnoFabricacao(ano);
        carro.setCor(cor);
        carro.setPlaca(placa);
        carro.setPortas(portas);
        carro.setModelo(modelo);

        return carro;
    }

    private Locacao createLocacao(LocalDate dataInicial, LocalDate dataFinal, Double valor, Veiculo veiculo) {
        Locacao locacao = new Locacao();
        locacao.setDataInicial(dataInicial);
        locacao.setDataFinal(dataFinal);
        locacao.setValor(valor);
        locacao.setVeiculo(veiculo);

        return locacao;
    }

    private Reserva createReserva(LocalDate dataInicial, LocalDate dataFinal, Veiculo veiculo) {
        Reserva reserva = new Reserva();
        reserva.setDataInicial(dataInicial);
        reserva.setDataFinal(dataFinal);
        reserva.setVeiculo(veiculo);

        return reserva;
    }
}
