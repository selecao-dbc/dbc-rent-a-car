package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import br.com.targettrust.traccadastros.service.LocacaoService;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = MOCK, classes = { TracCadastrosApplication.class })
public class LocacaoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private LocacaoService locacaoServiceMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testCreateStatusOk() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        Locacao locacao = mockLocacaoDTO();
        String jsonLocacao = TestUtils.convertLocacaoOuReservaDTOToJson(locacao);

        when(locacaoServiceMock.save(eq(null), any(LocacaoOuReservaDTO.class))).thenReturn(locacao);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLocacao));
    }

    @Test
    public void testCreateDTONotValidWithoutModelo() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO(null,
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'modelo' não pode ser vázio.\"]"));
    }

    @Test
    public void testCreateDTONotValidWithoutDataInicial() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO("Prisma",
                null, LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataInicial' não pode ser nulo.\"]"));
    }

    @Test
    public void testCreateDTONotValidWithoutDataFinal() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[\"'dataFinal' não pode ser nulo.\"]"));
    }

    @Test
    public void testCreateDTONotValidMoreOneErrors() throws Exception {
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO(null,
                null, null);
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        this.mockMvc.perform(post("/locacoes")
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
        LocacaoOuReservaDTO locacaoOuReservaDTO = TestUtils.mockLocacaoOuReservaDTO("Prisma",
                LocalDate.of(2020, 6, 12), LocalDate.of(2020, 6, 15));
        String jsonLocacaoOuReservaDTO = TestUtils.convertLocacaoOuReservaDTOToJson(locacaoOuReservaDTO);

        when(locacaoServiceMock.save(eq(null), any(LocacaoOuReservaDTO.class))).thenReturn(null);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoOuReservaDTO))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private Locacao mockLocacaoDTO() {
        Modelo modelo = new Modelo();
        modelo.setNome("Prisma");

        Carro carro = new Carro();
        carro.setPortas(4);
        carro.setCor("Azul");
        carro.setPlaca("PMO-1212");
        carro.setModelo(modelo);

        Locacao locacao = new Locacao();
        locacao.setDataInicial(LocalDate.of(2020, 6, 12));
        locacao.setDataFinal(LocalDate.of(2020, 6, 15));
        locacao.setVeiculo(carro);

        return locacao;
    }

}
