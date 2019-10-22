package br.com.targettrust.traccadastros.controller;

import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.entidades.Carro;
import br.com.targettrust.traccadastros.entidades.Locacao;
import br.com.targettrust.traccadastros.entidades.Modelo;
import br.com.targettrust.traccadastros.entidades.dto.LocacaoDTO;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        Locacao locacao = mockLocacaoDTO();
        String jsonLocacao = TestUtils.convertObjectToJson(locacao);

        when(locacaoServiceMock.save(eq(null), any(LocacaoDTO.class))).thenReturn(locacao);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLocacao));
    }

    @Test
    public void testCreateDTONotValidWithoutModelo() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO(null,
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'modelo' não pode ser vázio.")));
    }

    @Test
    public void testCreateDTONotValidWithoutDataInicial() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                null, LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' não pode ser nulo.")));
    }

    @Test
    public void testCreateDTONotValidWithoutDataFinal() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12), null, 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' não pode ser nulo.")));
    }

    @Test
    public void testCreateDTONotValidMoreOneErrors() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO(null,
                null, null, null);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(4)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' não pode ser nulo.")))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' não pode ser nulo.")))
                .andExpect(jsonPath("$.errors", hasItem("'modelo' não pode ser vázio.")))
                .andExpect(jsonPath("$.errors", hasItem("'valor' não pode ser nulo.")));
    }

    @Test
    public void testCreateDTONotValidWithInvalidDatas() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2010, 6, 12),
                LocalDate.of(2010, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' precisa ser uma data do futuro.")))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' precisa ser uma data atual ou do futuro.")));
    }

    @Test
    public void testCreateStatusNotFound() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        when(locacaoServiceMock.save(eq(null), any(LocacaoDTO.class))).thenReturn(null);

        this.mockMvc.perform(post("/locacoes")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateStatusOk() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        Locacao locacao = mockLocacaoDTO();
        String jsonLocacao = TestUtils.convertObjectToJson(locacao);

        when(locacaoServiceMock.save(eq(1L), any(LocacaoDTO.class))).thenReturn(locacao);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(jsonLocacao));
    }

    @Test
    public void testUpdateDTONotValidWithoutModelo() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO(null,
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'modelo' não pode ser vázio.")));
    }

    @Test
    public void testUpdateDTONotValidWithoutDataInicial() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                null, LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' não pode ser nulo.")));
    }

    @Test
    public void testUpdateDTONotValidWithoutDataFinal() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12), null, 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' não pode ser nulo.")));
    }

    @Test
    public void testUpdateDTONotValidMoreOneErrors() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO(null, null, null, null);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(4)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' não pode ser nulo.")))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' não pode ser nulo.")))
                .andExpect(jsonPath("$.errors", hasItem("'modelo' não pode ser vázio.")))
                .andExpect(jsonPath("$.errors", hasItem("'valor' não pode ser nulo.")));
    }

    @Test
    public void testUpdateDTONotValidWithInvalidDatas() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2010, 6, 12),
                LocalDate.of(2010, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("'dataFinal' precisa ser uma data do futuro.")))
                .andExpect(jsonPath("$.errors", hasItem("'dataInicial' precisa ser uma data atual ou do futuro.")));
    }

    @Test
    public void testUpdateStatusNotFound() throws Exception {
        LocacaoDTO locacaoDTO = mockLocacaoDTO("Prisma",
                LocalDate.of(2020, 6, 12),
                LocalDate.of(2020, 6, 15), 12D);
        String jsonLocacaoDTO = TestUtils.convertObjectToJson(locacaoDTO);

        when(locacaoServiceMock.save(eq(1L), any(LocacaoDTO.class))).thenReturn(null);

        this.mockMvc.perform(put("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(jsonLocacaoDTO))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteById() throws Exception {
        this.mockMvc.perform(delete("/locacoes/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(locacaoServiceMock, times(1)).deleteById(1L);
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

    public LocacaoDTO mockLocacaoDTO(String modelo, LocalDate dataInicial, LocalDate dataFinal, Double valor) {
        LocacaoDTO locacaoDTO = new LocacaoDTO();
        locacaoDTO.setModelo(modelo);
        locacaoDTO.setDataInicial(dataInicial);
        locacaoDTO.setDataFinal(dataFinal);
        locacaoDTO.setValor(valor);

        return locacaoDTO;
    }

}
