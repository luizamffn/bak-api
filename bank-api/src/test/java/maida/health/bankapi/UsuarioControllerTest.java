package maida.health.bankapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import maida.health.bankapi.controller.form.UsuarioForm;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	public void cadastrarNovoUsuario() throws Exception {
		UsuarioForm form = new UsuarioForm();
		form.setName("John Doe");
		form.setEmail("default@email.com");
		form.setSenha("123987");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());

	}
	
	@Test
	public void UsuarioJaCadastrado() throws Exception {
		UsuarioForm form = new UsuarioForm();
		form.setName("John Doe");
		form.setEmail("default@email.com");
		form.setSenha("123987");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void cadastrarNovoUsuarioEmailInvalido() throws Exception {
		UsuarioForm form = new UsuarioForm();
		form.setName("Joao");
		form.setEmail("default2email.com");
		form.setSenha("123987");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void cadastrarNovoUsuarioSenhaCurta() throws Exception {
		UsuarioForm form = new UsuarioForm();
		form.setName("Carla");
		form.setEmail("teste@email.com");
		form.setSenha("123");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
	
	@Test
	public void cadastrarNovoUsuarioCamposVazios() throws Exception {
		UsuarioForm form = new UsuarioForm();
		form.setName("");
		form.setEmail("");
		form.setSenha("");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());

	}
}
