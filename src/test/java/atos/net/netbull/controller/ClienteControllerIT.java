package atos.net.netbull.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
public class ClienteControllerIT {

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
	@DisplayName("Update retorna clienteVO quando id existe")
	public void test_update_retorna_clienteVO_quando_id_existe() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setNome("carlos");
		cliente.setCpf("776.153.110-20");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.of(1990, 10, 10));
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

		ClienteVO clienteAlterado = new ClienteVO();

		clienteAlterado.setNome("Carlos Santana ");
		clienteAlterado.setCpf("776.153.110-20");
		clienteAlterado.setDtCriacao(LocalDateTime.now());
		clienteAlterado.setDtNascimento(LocalDate.of(1990, 10, 10));
		clienteAlterado.setEmail("mm@gmail.com");
		clienteAlterado.setTelefone("11999999999");
		clienteAlterado.setTipo(TipoClienteEnum.PF);

		EnderecoVO enderecoAlterado = new EnderecoVO();

		enderecoAlterado.setLogradouro("Av. Paulista");
		enderecoAlterado.setNumero("505");
		enderecoAlterado.setComplemento("masp");
		enderecoAlterado.setBairro("Bela Vista");
		enderecoAlterado.setCidade("São Paulo");
		enderecoAlterado.setEstado("SP");
		enderecoAlterado.setCep("01311-000");
		enderecoAlterado.setTipo(TipoEnderecoEnum.RESIDENCIA);

		clienteAlterado.addEndereco(enderecoAlterado);

		jsonBody = mapper.writeValueAsString(clienteAlterado);

		ResultActions resultadoUpdate = mockMvc.perform(put("/v1/clientes/{id}", clienteDevolvido.getId())
				.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultadoUpdate.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Update retorna BadRequest quando id não existe")
	public void test_update_retorna_BadReques_quando_id_existe() throws Exception {

		ResultActions resultado = mockMvc.perform(put("/v1/clientes/{id}", 99L).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("Cria cliente retorna BadRequest quando campos vazios")
	public void test_criaCliente_retorna_BadReques_quando_id_existe() throws Exception {

		ResultActions resultado = mockMvc.perform(
				post("/v1/clientes").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isBadRequest());

	}

	@Test
	@DisplayName("Cria cliente PF")
	public void test_criaClientePF_retorna_retorno_criado() throws Exception {

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
	}

	@Test
	@DisplayName("Cria cliente PJ")
	public void test_criaClientePJ_retorna_retorno_criado() throws Exception {

		ClienteVO cliente = new ClienteVO();

		cliente.setTipo(TipoClienteEnum.PJ);
		cliente.setRazaoSocial("Garcia Auto Peças Ltda.");
		cliente.setCnpj("36485583000135");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		cliente.setTipo(TipoClienteEnum.PJ);

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
	}

	public void test_bucarcar_todos_deve_retornar_busca_paginada() throws Exception {
		ResultActions result = mockMvc.perform(get("/v1/clientes/?page=2&size=3").accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
	}

	@Test
	@DisplayName("Testa deleta cliente existente")
	public void test_deleta_cliente() throws Exception {

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

		ResultActions resultadoDelete = mockMvc.perform(delete("/v1/clientes/{id}", clienteDevolvido.getId())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultadoDelete.andExpect(status().isNoContent());
	}

	@Test
	@DisplayName("Testa quando delete não encontra cliente")
	public void test_quando_deleta_nao_encontra_cliente() throws Exception {

		ResultActions resultado = mockMvc.perform(delete("/v1/clientes/{id}", 990L)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isNotFound());
	}
}
