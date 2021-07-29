package maida.health.bankapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import maida.health.bankapi.controller.form.LoginForm;

@SpringBootTest
@AutoConfigureMockMvc
public class AutenticacaoControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void LoginDoUsuario() throws JsonProcessingException, Exception {
		LoginForm form = new LoginForm();		
		form.setEmail("default@email.com");
		form.setSenha("123987");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/auth")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void LoginSenhaIncorreta() throws JsonProcessingException, Exception {
		LoginForm form = new LoginForm();		
		form.setEmail("default@email.com");
		form.setSenha("12345");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/auth")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
