package atos.net.netbull.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

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
import atos.net.netbull.domain.TipoEnderecoEnum;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CriaClienteServiceTest {
	
	private CriaClienteService criaClienteServ;
	private ClienteRepository repositorio;
	private Validator validator;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.getValidator();
	}
	
	@BeforeEach
	public void  iniciarCadaTeste() {
		this.repositorio = Mockito.mock(ClienteRepository.class);	
		this.criaClienteServ = new CriaClienteService(validator, this.repositorio);
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
	@DisplayName("Testa os campos do cliente com obrigatoriedade.")
	void test_quando_TodosCamposObrigatorios_Eh_Null_LancarExcecao() {
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		
		var assertThrows =assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
		
		assertEquals(7, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"Campo tipo de cliente não pode ser nulo",
				"Campo nome não pode ser nulo",
				"Campo cpf não pode ser nulo",
				"Campo data de nascimento não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo telefone não pode ser nulo",
				"Campo Endereço não pode ser nulo"
				));
	}
	
	@Test
	@DisplayName("Testa os campos especificos de PF com obrigatoriedade.")
	void test_quando_TodosCamposPfObrigatorios_Eh_Null_LancarExcecao() {
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setTipo(TipoClienteEnum.PF);
		
		var assertThrows =assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
		
		assertEquals(6, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"Campo nome não pode ser nulo",
				"Campo cpf não pode ser nulo",
				"Campo data de nascimento não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo telefone não pode ser nulo",
				"Campo Endereço não pode ser nulo"));
	}
	
	@Test
	@DisplayName("Testa os campos especificos de PJ com obrigatoriedade.")
	void test_quando_TodosCamposPjObrigatorios_Eh_Null_LancarExcecao() {
		assertNotNull(criaClienteServ);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setTipo(TipoClienteEnum.PJ);
		
		var assertThrows =assertThrows(ConstraintViolationException.class, ()->
		criaClienteServ.persistir(cliente));
		
		assertEquals(5, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems(
				"Campo cnpj não pode ser nulo",
				"Campo telefone não pode ser nulo",
				"Campo Endereço não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo razão social não pode ser nulo"));
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
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
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
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
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
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
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
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		cliente.addEndereco(endereco);
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
		criaClienteServ.persistir(cliente));

		assertEquals(assertThrows.getMessage(),"O telefone informado não é válido");
	}
	
	@Test
	@DisplayName("Testa persistencia do cliente")
	void test_quando_Clinte_criado() {
		
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
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		cliente.addEndereco(endereco);
		
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();
		
		when(this.repositorio.save(any())).thenReturn(clienteEntity);
		
		ClienteVO criado = criaClienteServ.persistir(cliente);

		then(repositorio).should(times(1)).save(any());
		assertNotNull(criado);
	}


}
