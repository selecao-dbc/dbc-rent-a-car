package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.Reserva;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.service.ReservaService;
import br.com.targettrust.traccadastros.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = { TracCadastrosApplication.class })
public class ReservaControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ReservaService reservaServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testCreateStatusOk() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        Reserva reserva = mockReservaDTO();
        String jsonLocacao = TestUtils.convertObjectToJson(reserva);

        when(reservaServiceMock.save(eq(null), any(LocacaoOuReservaDTO.class))).thenReturn(reserva);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLocacao));
    }

    @Test
    public void testCreateDTONotValidWithoutModelo() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO(null,
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'modelo' não pode ser vázio.\"]"));
    }

    @Test
    public void testCreateDTONotValidWithoutDataInicial() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                null, LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataInicial' não pode ser nulo.\"]"));
    }

    @Test
    public void testCreateDTONotValidWithoutDataFinal() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataFinal' não pode ser nulo.\"]"));
    }

    @Test
    public void testCreateDTONotValidMoreOneErrors() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO(null,
                null, null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "[\"'dataFinal' não pode ser nulo.\"," +
                                "\"'dataInicial' não pode ser nulo.\"," +
                                "\"'modelo' não pode ser vázio.\"]"));
    }

    @Test
    public void testCreateStatusNotFound() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        when(reservaServiceMock.save(eq(null), any(LocacaoOuReservaDTO.class))).thenReturn(null);

        this.mockMvc.perform(post("/reservas")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateStatusOk() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        Reserva reserva = mockReservaDTO();
        String jsonLocacao = TestUtils.convertObjectToJson(reserva);

        when(reservaServiceMock.save(eq(1L), any(LocacaoOuReservaDTO.class))).thenReturn(reserva);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLocacao));
    }

    @Test
    public void testUpdateDTONotValidWithoutModelo() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO(null,
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'modelo' não pode ser vázio.\"]"));
    }

    @Test
    public void testUpdateDTONotValidWithoutDataInicial() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                null, LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataInicial' não pode ser nulo.\"]"));
    }

    @Test
    public void testUpdateDTONotValidWithoutDataFinal() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataFinal' não pode ser nulo.\"]"));
    }

    @Test
    public void testUpdateDTONotValidMoreOneErrors() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO(null,
                null, null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(
                        "[\"'dataFinal' não pode ser nulo.\"," +
                                "\"'dataInicial' não pode ser nulo.\"," +
                                "\"'modelo' não pode ser vázio.\"]"));
    }

    @Test
    public void testUpdateStatusNotFound() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertObjectToJson(locacaoOuReservaDTO);

        when(reservaServiceMock.save(eq(1L), any(LocacaoOuReservaDTO.class))).thenReturn(null);

        this.mockMvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCancelWithReservaFound() throws Exception {
        when(reservaServiceMock.cancel(eq(1L))).thenReturn(Optional.of(new Reserva()));

        this.mockMvc.perform(put("/reservas/1/cancel")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelWithReservaNotFound() throws Exception {
        when(reservaServiceMock.cancel(eq(1L))).thenReturn(Optional.empty());

        this.mockMvc.perform(put("/reservas/1/cancel")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteById() throws Exception {
        this.mockMvc.perform(delete("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(reservaServiceMock, times(1)).deleteById(1L);
    }

    private Reserva mockReservaDTO() {
        Modelo modelo = new Modelo();
        modelo.setNome("Prisma");

        Carro carro = new Carro();
        carro.setPortas(4);
        carro.setCor("Azul");
        carro.setPlaca("PMO-1212");
        carro.setModelo(modelo);

        Reserva reserva = new Reserva();
        reserva.setDataInicial(LocalDate.of(2020, 6, 12));
        reserva.setDataFinal(LocalDate.of(2020, 6, 15));
        reserva.setVeiculo(carro);

        return reserva;
    }

    public LocacaoOuReservaDTO mockLocacaoOuReservaDTO(String modelo, LocalDate dataInicial, LocalDate dataFinal) {
        LocacaoOuReservaDTO locacaoOuReservaDTO = new LocacaoOuReservaDTO();
        locacaoOuReservaDTO.setModelo(modelo);
        locacaoOuReservaDTO.setDataInicial(dataInicial);
        locacaoOuReservaDTO.setDataFinal(dataFinal);

        return locacaoOuReservaDTO;
    }

}
