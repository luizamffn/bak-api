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

import maida.health.bankapi.controller.form.LoginForm;
import maida.health.bankapi.controller.form.TransferenciaForm;

@SpringBootTest
@AutoConfigureMockMvc
public class TransferenciaControllerTeste {
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
	public void novaTransferencia() throws Exception {
		TransferenciaForm form = new TransferenciaForm();
		form.setSource_account_number("1234-5");
		form.setDestination_account_number("1234-1");
		form.setAmount(new BigDecimal(20));
				
		Map<?, ?> loginUsuario = recuperarUsuario();

		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
				.content(objectMapper.writeValueAsString(form))
				.header("Authorization", "Bearer " + loginUsuario.get("token").toString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	public void novaTransferenciaValorNegativo() throws Exception {
		TransferenciaForm form = new TransferenciaForm();
		form.setSource_account_number("1234-5");
		form.setDestination_account_number("1234-1");
		form.setAmount(new BigDecimal(-20));
				
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
				.header("Authorization", "Bearer " + recuperarUsuario().get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void novaTransferenciaDestinoNaoEncontrado() throws Exception {
		TransferenciaForm form = new TransferenciaForm();
		form.setSource_account_number("1234-5");
		form.setDestination_account_number("1234-14");
		form.setAmount(new BigDecimal(20));
				
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
				.header("Authorization", "Bearer " + recuperarUsuario().get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	public void novaTransferenciaUsuarioTranferenciaEncontrado() throws Exception {
		TransferenciaForm form = new TransferenciaForm();
		form.setSource_account_number("1234-53");
		form.setDestination_account_number("1234-1");
		form.setAmount(new BigDecimal(20));
				
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/transfer")
				.header("Authorization", "Bearer " + recuperarUsuario().get("token").toString())
				.content(objectMapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
