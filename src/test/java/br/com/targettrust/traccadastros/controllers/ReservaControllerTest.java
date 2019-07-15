package br.com.targettrust.traccadastros.controllers;

import br.com.targettrust.traccadastros.TracCadastrosApplication;
import br.com.targettrust.traccadastros.servicos.ReservaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TracCadastrosApplication.class)
@AutoConfigureMockMvc
public class ReservaControllerTest {

    private final String JSON_VALID_REQUEST = "{ \"dataFinal\": \"2019-10-20\", \"dataInicial\": \"2019-10-21\", \"idModelo\": 1 }";

    private final String JSON_INVALID_REQUEST = "{ \"dataFinal\": \"2019-10-20\", \"dataInicial\": null, \"idModelo\": 1 }";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservaService service;

    @Test
    public void reservarTest() throws Exception {
        mvc.perform(post("/reservas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_VALID_REQUEST))
                .andExpect(status().isCreated());
    }

    @Test
    public void reservarBadRequestTest() throws Exception {
        mvc.perform(post("/reservas/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_INVALID_REQUEST))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void editarTest() throws Exception {
        mvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_VALID_REQUEST))
                .andExpect(status().isOk());
    }

    @Test
    public void editarBadRequestTest() throws Exception {
        mvc.perform(put("/reservas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON_INVALID_REQUEST))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cancelarTest() throws Exception {
        mvc.perform(post("/reservas/1/cancelar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
