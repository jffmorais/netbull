package atos.net.netbull.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoEnderecoEnum;
import atos.net.netbull.service.DeletaClienteService;


//@AutoConfigureMockMvc
//@Transactional
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestPropertySource("classpath:application-test.properties")
//@ActiveProfiles("test")

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteControllerIT {

	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
    private ObjectMapper mapper;
	
    private MockMvc mockMvc;
    
    @Autowired
    private DeletaClienteService deletaClienteService;
    
    @Autowired
    private EntityManager entityManager;
    
    @BeforeAll
    public void setup() {
    	
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        assertNotNull(this.entityManager);
        

    }
    


	@Test
	@DisplayName("update retorna clienteVO quando id existe")
	public void test_update_retorna_clienteVO_quando_id_existe() throws Exception{
		
	    ClienteVO cliente = new ClienteVO();
	    
	    cliente.setNome("carlos");
	    cliente.setCpf("123465678911");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now());
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		
		EnderecoVO endereco = new EnderecoVO();
		
		endereco.setLogradouro("av paulista");
		endereco.setNumero(500);
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		cliente.add(endereco);
		
		String jsonBody = mapper.writeValueAsString(cliente);
	
		String nomeEsperado = cliente.getNome();
		String cpfEsperado = cliente.getCpf();
		System.out.println(nomeEsperado);
		
		ResultActions resultado = 
				 mockMvc.perform(put("/v1/clientes/{id}", 5L)
						 .content(jsonBody)
						 .contentType(MediaType.APPLICATION_JSON)
						 .accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.nome").value(nomeEsperado));
		resultado.andExpect(jsonPath("$.cpf").value(cpfEsperado));
		
		
	}
	
	@Test
	@DisplayName("update retorna BadRequest quando id não existe")
	public void test_update_retorna_BadReques_quando_id_existe() throws Exception{
		
	
		
		ResultActions resultado = 
				 mockMvc.perform(put("/v1/clientes/{id}", 99L)
						 .contentType(MediaType.APPLICATION_JSON)
						 .accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isBadRequest());

		
		
	}
	
	@Test
	@DisplayName("cria cliente retorna BadRequest quando campos vazios")
	public void test_criaCliente_retorna_BadReques_quando_id_existe() throws Exception{
		
	
		
		ResultActions resultado = 
				 mockMvc.perform(post("/v1/clientes")
						 .contentType(MediaType.APPLICATION_JSON)
						 .accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isBadRequest());

		
		
	}
	
	@Test
	@DisplayName("Cria cliente")
	public void test_criaCliente_retorna_retorno_criado() throws Exception{
		
	    ClienteVO cliente = new ClienteVO();
	    
	    cliente.setNome("carlos");
	    cliente.setCpf("123465678911");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setDtNascimento(LocalDate.now());
		cliente.setEmail("mm@gmail.com");
		cliente.setTelefone("11999999999");
		
		EnderecoVO endereco = new EnderecoVO();
		
		endereco.setLogradouro("av paulista");
		endereco.setNumero(500);
		endereco.setComplemento("masp");
		endereco.setBairro("Bela Vista");
		endereco.setCidade("São Paulo");
		endereco.setEstado("SP");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		cliente.add(endereco);
		
		String jsonBody = mapper.writeValueAsString(cliente);
	
		String nomeEsperado = cliente.getNome();
		String cpfEsperado = cliente.getCpf();
		System.out.println(nomeEsperado);
		
		ResultActions resultado = 
				 mockMvc.perform(post("/v1/clientes/")
						 .content(jsonBody)
						 .contentType(MediaType.APPLICATION_JSON)
						 .accept(MediaType.APPLICATION_JSON));
		
		resultado.andExpect(status().isCreated());
		
		
		
	}
	


		
		
	
    
}
