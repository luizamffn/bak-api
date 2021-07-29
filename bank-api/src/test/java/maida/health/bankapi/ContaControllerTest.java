package maida.health.bankapi;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import maida.health.bankapi.controller.form.ContaForm;
import maida.health.bankapi.controller.form.LoginForm;
import maida.health.bankapi.controller.form.SaldoForm;

@SpringBootTest
@AutoConfigureMockMvc
public class ContaControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Map<?, ?> recuperarUsuario() throws JsonProcessingException, Exception {
		LoginForm form = new LoginForm();		
		form.setEmail("default@email.com");
		form.setSenha("123987");
		
		MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/auth")
				  .content(objectMapper.writeValueAsString(form))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)).andReturn();
		
		return new ObjectMapper().readValue(response.getResponse().getContentAsString(), Map.class);		
	}
	
	
	@Test
	public void cadastrarNovaContaComToken() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("1234-5");
		form.setBalance(new BigDecimal(100));
				
		Map<?, ?> loginUsuario = recuperarUsuario();
		
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void cadastrarNovaContaSemToken() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("12345-8");
		form.setBalance(new BigDecimal(100));
				
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}
	
	@Test
	public void cadastrarContaExistente() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("1234-5");
		form.setBalance(new BigDecimal(100));
				
		Map<?, ?> loginUsuario = recuperarUsuario();

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void cadastrarContaSemBalance() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("1234-5");
		form.setBalance(null);
				
		Map<?, ?> loginUsuario = recuperarUsuario();

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void cadastrarContaComBalanceNegativo() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("1234-5");
		form.setBalance(new BigDecimal(-20));
		
		Map<?, ?> loginUsuario = recuperarUsuario();

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void cadastrarContaSemNumero() throws Exception {
		ContaForm form = new ContaForm();
		form.setNumber("");
		form.setBalance(new BigDecimal(100));
				
		Map<?, ?> loginUsuario = recuperarUsuario();

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void consultarSaldo() throws Exception {
		SaldoForm form = new SaldoForm();
		form.setAccount_number("1234-5");
		
		Map<?, ?> loginUsuario = recuperarUsuario();

						
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void consultarSaldoContaInvalida() throws Exception {
		SaldoForm form = new SaldoForm();
		form.setAccount_number("1234-54");
		
		Map<?, ?> loginUsuario = recuperarUsuario();

						
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/balance")
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
}
