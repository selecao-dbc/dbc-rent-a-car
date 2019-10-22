package br.com.targettrust.traccadastros.util;

import br.com.targettrust.traccadastros.entidades.dto.LocacaoOuReservaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        JavaTimeModule module = new JavaTimeModule();
        LocalDateDeserializer localDateDeserializer =  new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        ObjectMapper om = Jackson2ObjectMapperBuilder.json()
                .modules(module)
                .build();
        return om.writeValueAsString(object);
    }

    private TestUtils() {}
}
