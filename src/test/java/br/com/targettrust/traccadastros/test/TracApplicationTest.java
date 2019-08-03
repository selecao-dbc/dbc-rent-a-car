package br.com.targettrust.traccadastros.test;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource("classpath:application.properties")
public class TracApplicationTest {
	
	@Value("${local.server.port}")
	protected int porta;

	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {	
		System.out.println("porta " + porta);
	}
}
