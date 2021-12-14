package br.com.vr.miniautorizador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.vr.miniautorizador.controller.form.CartaoForm;
import br.com.vr.miniautorizador.controller.form.TransacaoForm;
import br.com.vr.miniautorizador.exception.CartaoExistenteException;
import br.com.vr.miniautorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.exception.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exception.SenhaInvalidaException;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.service.CartoesService;
import br.com.vr.miniautorizador.service.TransacoesService;

@SpringBootTest
@AutoConfigureMockMvc
class MiniautorizadorApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartoesService cartoesService;
    
    @Autowired
    private TransacoesService transacoesService;
	
    @Test
	void cadastrarCartaoComSucesso() throws Exception{
		CartaoForm cartaoForm = new CartaoForm("77777", "123433333");
		
		mockMvc.perform(post("/cartoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(cartaoForm)))
		        .andExpect(status().isCreated())
		        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cartaoForm)));
	}
    
	@Test
	void cadastrarCartaoExistente() throws Exception{
		CartaoForm cartaoForm = new CartaoForm("6549873025634501", "1234");
		Cartao cartao = cartaoForm.converter();
		
		mockMvc.perform(post("/cartoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(cartao)))
		        .andExpect(status().isUnprocessableEntity());
		
		Assertions.assertThrows(CartaoExistenteException.class, () -> cartoesService.cadastrar(cartao));
	}
	
	@Test
	void obterSaldoComSucesso() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/cartoes/77777")
		        .contentType(MediaType.TEXT_HTML))
		        .andExpect(status().isOk());
		
		Assertions.assertEquals("500.00", cartoesService.obterSaldo("77777"));
	}
	
	@Test
	void obterSaldoComCartaoInexistente() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/cartoes/09090909090")
		        .contentType(MediaType.TEXT_HTML))
		        .andExpect(status().isNotFound());
		
		Assertions.assertThrows(CartaoInexistenteException.class, () -> cartoesService.obterSaldo("09090909090"));
	}
	
	@Test
	void transacaoRealizadaComSucesso() throws Exception{
		TransacaoForm transacaoForm = new TransacaoForm("6549873025634501", "1234", new BigDecimal(10.00));
		
		mockMvc.perform(post("/transacoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(transacaoForm)))
		        .andExpect(status().isCreated())
		        .andExpect(MockMvcResultMatchers.content().string("OK"));
	}

	@Test
	void transacaoComSaldoInsuficiente() throws Exception{
		TransacaoForm transacaoForm = new TransacaoForm("6549873025634501", "1234", new BigDecimal(1000.00));
		
		mockMvc.perform(post("/transacoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(transacaoForm)))
		        .andExpect(status().isUnprocessableEntity());
		
		Assertions.assertThrows(SaldoInsuficienteException.class, () -> transacoesService.transacao(transacaoForm));
	}
	
	@Test
	void transacaoComSenhaInvalida() throws Exception{
		TransacaoForm transacaoForm = new TransacaoForm("6549873025634501", "12349999999999", new BigDecimal(10.00));
		
		mockMvc.perform(post("/transacoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(transacaoForm)))
		        .andExpect(status().isUnprocessableEntity());
		
		Assertions.assertThrows(SenhaInvalidaException.class, () -> transacoesService.transacao(transacaoForm));
	}
	
	@Test
	void transacaoComCartaoInexistente() throws Exception{
		TransacaoForm transacaoForm = new TransacaoForm("0", "1234", new BigDecimal(10.00));
		
		mockMvc.perform(post("/transacoes")
		        .contentType("application/json")
		        .content(objectMapper.writeValueAsString(transacaoForm)))
		        .andExpect(status().isUnprocessableEntity());
		
		Assertions.assertThrows(CartaoInexistenteException.class, () -> transacoesService.transacao(transacaoForm));
	}
}
