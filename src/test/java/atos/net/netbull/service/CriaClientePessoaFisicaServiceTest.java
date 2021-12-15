package atos.net.netbull.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoClienteEnum;
import atos.net.netbull.repository.ClientePessoaFisicaRepository;
import atos.net.netbull.repository.entity.ClientePessoaFisicaEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CriaClientePessoaFisicaServiceTest {

	private Validator validator;
	private CriaClientePessoaFisicaService criaClienteServ;
	private ClientePessoaFisicaRepository clienteRepo;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = 
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();	
	}
	
	@BeforeEach
	public void  iniciarCadaTeste() {	
		this.clienteRepo = Mockito.mock(ClientePessoaFisicaRepository.class);
		this.criaClienteServ = new CriaClientePessoaFisicaService(this.validator, this.clienteRepo);
	}
	
	@Test
	@DisplayName("Testa quando o cliente PF é nulo.")
	void test_quando_cliente_Eh_Null_LancarExcecao() {
		assertNotNull(criaClienteServ);

		ClienteVO cliente =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
			criaClienteServ.persistir(cliente));
		
		assertNotNull(assertThrows);
	}
	
	@Test
	@DisplayName("Testa os campos do cliente PF com obrigatoriedade.")
	void test_quando_TodosCamposObrigatorios_Eh_Null_LancarExcecao() {
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		
		
		var assertThrows =assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
		
		assertEquals(7, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		assertThat(mensagens, hasItems("Campo tipo de cliente não pode ser nulo",
				"Campo nome não pode ser nulo",
				"Campo cpf não pode ser nulo",
				"Campo data de nascimento não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo telefone não pode ser nulo",
				"Campo Endereço não pode ser nulo"));
	}
	
	@Test
	@DisplayName("Testa se cliente PF é maior de 12 anos.")
	void test_quando_DataNascimento_eh_depois_dataMinima_LancaExcecao() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("Enzo Dias");
		cliente.setCpf("941.623.878-34");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("enzo@email.com");
		cliente.setTelefone("1199824285");
		cliente.setDtNascimento(LocalDate.parse("2010-12-06"));
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		
		cliente.addEndereco(endereco);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
		criaClienteServ.persistir(cliente));

		assertEquals(assertThrows.getMessage(),"A idade minima para cadastro de cliente é de 12 anos");
	}
	
	@Test
	@DisplayName("Testa se o CPF informado é valido")
	void test_quando_CPF_eh_invalido_LancaExcecao() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("João Dias");
		cliente.setCpf("11111111111");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("1199824285");
		cliente.setDtNascimento(LocalDate.parse("1965-11-08"));
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		
		cliente.addEndereco(endereco);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
		criaClienteServ.persistir(cliente));

		assertEquals(assertThrows.getMessage(),"O CPF informado não é válido");
	}
	
	@Test
	@DisplayName("Testa a quantidade máxima de endereços")
	void test_execede_qtd_max_enderecos_LancaExcecao() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("João Dias");
		cliente.setCpf("621.138.686-95");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("1199824285");
		cliente.setDtNascimento(LocalDate.parse("1965-11-08"));
		
		cliente.addEndereco(new EnderecoVO());
		cliente.addEndereco(new EnderecoVO());
		cliente.addEndereco(new EnderecoVO());
		cliente.addEndereco(new EnderecoVO());
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
	
		assertEquals(1, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		assertThat(mensagens, hasItems("Deve-se adicionar ao menos um e no máximo três endereços"));		
	}
	
	@Test
	@DisplayName("Testa validade do email informado")
	void test_email_invalido_LancaExcecao() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("João Dias");
		cliente.setCpf("621.138.686-95");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("emaildojoao");
		cliente.setTelefone("1199824285");
		cliente.setDtNascimento(LocalDate.parse("1965-11-08"));
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		
		cliente.addEndereco(endereco);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
		
		assertEquals(1, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Campo Email precisa ser válido"));
	}
	
	@Test
	@DisplayName("Testa validade do numero de telefone informado")
	void test_telefone_invalido_LancaExcecao() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("João Dias");
		cliente.setCpf("621.138.686-95");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("94285");
		cliente.setDtNascimento(LocalDate.parse("1965-11-08"));
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		
		cliente.addEndereco(endereco);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
		criaClienteServ.persistir(cliente));

		assertEquals(assertThrows.getMessage(),"O telefone informado não é válido");
	}
	
	@Test
	@DisplayName("Testa persistencia do cliente")
	void test_quando_Clinte_criadoo() {
		
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("João Dias");
		cliente.setCpf("621.138.686-95");
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setTipo(TipoClienteEnum.PF);
		cliente.setEmail("joao@email.com");
		cliente.setTelefone("1199824285");
		cliente.setDtNascimento(LocalDate.parse("1965-11-08"));
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		
		cliente.addEndereco(endereco);
		
		ClienteVO clienteCriado = criaClienteServ.persistir(cliente);
		
		assertNotNull(clienteCriado);
	}

}
