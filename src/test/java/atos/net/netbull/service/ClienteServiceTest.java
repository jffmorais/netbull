package atos.net.netbull.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import atos.net.netbull.domain.ClienteVO;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClienteServiceTest {
	
	private CriaClienteService criaCliente;
	
	private Validator validator;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactory.getValidator();
	}
	
	@BeforeEach
	public void  iniciarCadaTeste() {
		
		
		
		criaCliente = new CriaClienteService(validator);
	}
	
	@Test
	@DisplayName("Testa os campos do cliente com obrigatoriedade.")
	void test() {
		assertNotNull(criaCliente);
		
		ClienteVO cliente = new ClienteVO();
		
		
		var assertThrows =assertThrows(ConstraintViolationException.class, ()->
		criaCliente.persistir(cliente));
		
		assertEquals(6, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations()
				.stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		assertThat(mensagens, hasItems("Campo nome não pode ser nulo",
				"Campo cpf não pode ser nulo",
				"Campo data de nascimento não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo telefone não pode ser nulo",
				"Campo Itens deve ter pelo menos um item"));
	}
	
	
	@Test
	@DisplayName("Testa quando o cliente é nulo.")
	void test_quando_cliente_Eh_Null_LancarExcecao() {
		assertNotNull(criaCliente);

		ClienteVO cliente =  null;

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
			criaCliente.persistir(cliente));
		
		assertNotNull(assertThrows);
	}
	
	
	
	


}
