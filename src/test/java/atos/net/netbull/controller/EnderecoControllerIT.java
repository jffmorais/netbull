package atos.net.netbull.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoClienteEnum;
import atos.net.netbull.domain.TipoEnderecoEnum;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class EnderecoControllerIT {

	private static final String URI_ADICIONA = "/v1/endereco/adicionar/{id}";
	private static final String URI_ALTERA = "/v1/endereco/editar/{clienteId}/{enderecoId}";
	private static final String URI_DELETA = "/v1/endereco/remover/{clienteId}/{enderecoId}";

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Autowired
	private EntityManager entityManager;

	@BeforeAll
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		assertNotNull(this.entityManager);
	}

	@Test
	@DisplayName("Adiciona Endereço sem os campos obrigatórios")
	public void test_envioCamposSemDados_retorna400() throws Exception {
		EnderecoVO endereco = new EnderecoVO();

		this.mockMvc
				.perform(MockMvcRequestBuilders.post(URI_ADICIONA, 99l).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(endereco)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Adiciona novo endereco ao cliente")
	public void test_adicionaEndereco_retorna_retorno_criado() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isCreated());

		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		EnderecoVO enderecoAdd = new EnderecoVO();

		enderecoAdd.setLogradouro("Av. Doutor Rubens Filho");
		enderecoAdd.setNumero("1506");
		enderecoAdd.setComplemento("masp");
		enderecoAdd.setBairro("Bela Vista");
		enderecoAdd.setCidade("São Paulo");
		enderecoAdd.setEstado("SP");
		enderecoAdd.setCep("01311-000");
		enderecoAdd.setTipo(TipoEnderecoEnum.COBRANCA);

		jsonBody = mapper.writeValueAsString(enderecoAdd);

		ResultActions resultadoAdd = mockMvc.perform(post(URI_ADICIONA, clienteDevolvido.getId()).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultadoAdd.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Altera endereco existente")
	public void test_alteraEndereco_retorna_retorno_criado() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		
		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		resultado.andExpect(status().isCreated());
		
		EnderecoVO enderecoAltera = new EnderecoVO();

		enderecoAltera.setLogradouro("Av. Doutor Rubens Filho");
		enderecoAltera.setNumero("1506");
		enderecoAltera.setComplemento("masp");
		enderecoAltera.setBairro("Bela Vista");
		enderecoAltera.setCidade("São Paulo");
		enderecoAltera.setEstado("SP");
		enderecoAltera.setCep("01311-000");
		enderecoAltera.setTipo(TipoEnderecoEnum.COBRANCA);

		jsonBody = mapper.writeValueAsString(enderecoAltera);
		
		Iterator<EnderecoVO> iter = clienteDevolvido.getEnderecos().iterator();

		ResultActions resultadoAltera = mockMvc.perform(MockMvcRequestBuilders.put(URI_ALTERA, clienteDevolvido.getId(), iter.next().getId()).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultadoAltera.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Altera endereco Inexistente")
	public void test_alteraEndereco_retorna_retorno_NotFoundException() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		
		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		resultado.andExpect(status().isCreated());
		
		EnderecoVO enderecoAltera = new EnderecoVO();

		enderecoAltera.setLogradouro("Av. Doutor Rubens Filho");
		enderecoAltera.setNumero("1506");
		enderecoAltera.setComplemento("masp");
		enderecoAltera.setBairro("Bela Vista");
		enderecoAltera.setCidade("São Paulo");
		enderecoAltera.setEstado("SP");
		enderecoAltera.setCep("01311-000");
		enderecoAltera.setTipo(TipoEnderecoEnum.COBRANCA);

		jsonBody = mapper.writeValueAsString(enderecoAltera);

		ResultActions resultadoAltera = mockMvc.perform(MockMvcRequestBuilders.put(URI_ALTERA, clienteDevolvido.getId(), 3).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultadoAltera.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Deleta endereco de cliente com dois endereços")
	public void test_deletaEndereco_retorna_retorno_criado() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);
		
		EnderecoVO segundoEndereco = new EnderecoVO();

		segundoEndereco.setLogradouro("Av. Doutor Rubens Filho");
		segundoEndereco.setNumero("1506");
		segundoEndereco.setComplemento("masp");
		segundoEndereco.setBairro("Bela Vista");
		segundoEndereco.setCidade("São Paulo");
		segundoEndereco.setEstado("SP");
		segundoEndereco.setCep("01311-000");
		segundoEndereco.setTipo(TipoEnderecoEnum.COBRANCA);
		
		cliente.addEndereco(segundoEndereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		
		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		resultado.andExpect(status().isCreated());
		
		Iterator<EnderecoVO> iter = clienteDevolvido.getEnderecos().iterator();
		
		ResultActions resultadoDelete = mockMvc.perform(MockMvcRequestBuilders.delete(URI_DELETA, clienteDevolvido.getId(), iter.next().getId()));
				
		resultadoDelete.andExpect(status().isNoContent());
	}
	
	@Test
	@DisplayName("Deleta endereco de cliente com apenas um endereço")
	public void test_deletaEndereco_retorna_retorno_BadRequestException() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		
		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		resultado.andExpect(status().isCreated());
		
		Iterator<EnderecoVO> iter = clienteDevolvido.getEnderecos().iterator();
		
		ResultActions resultadoDelete = mockMvc.perform(MockMvcRequestBuilders.delete(URI_DELETA, clienteDevolvido.getId(), iter.next().getId()));
				
		resultadoDelete.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deleta endereco inexistente")
	public void test_deletaEndereco_retorna_retorno_NotFoundException() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("978.043.320-17");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now().minus(18, ChronoUnit.YEARS));
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PF);

		EnderecoVO endereco = new EnderecoVO();

		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		cliente.addEndereco(endereco);
		
		EnderecoVO segundoEndereco = new EnderecoVO();

		segundoEndereco.setLogradouro("Av. Doutor Rubens Filho");
		segundoEndereco.setNumero("1506");
		segundoEndereco.setComplemento("masp");
		segundoEndereco.setBairro("Bela Vista");
		segundoEndereco.setCidade("São Paulo");
		segundoEndereco.setEstado("SP");
		segundoEndereco.setCep("01311-000");
		segundoEndereco.setTipo(TipoEnderecoEnum.COBRANCA);
		
		cliente.addEndereco(segundoEndereco);

		String jsonBody = mapper.writeValueAsString(cliente);

		ResultActions resultado = mockMvc.perform(post("/v1/clientes/").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		
		ClienteVO clienteDevolvido = mapper.readValue(resultado.andReturn().getResponse().getContentAsString(),
				ClienteVO.class);

		resultado.andExpect(status().isCreated());
		
		ResultActions resultadoDelete = mockMvc.perform(MockMvcRequestBuilders.delete(URI_DELETA, clienteDevolvido.getId(), 3));
				
		resultadoDelete.andExpect(status().isNotFound());
	}
}
