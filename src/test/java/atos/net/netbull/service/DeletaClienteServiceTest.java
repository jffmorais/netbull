package atos.net.netbull.service;

import static org.mockito.Mockito.verify;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import atos.net.netbull.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class DeletaClienteServiceTest {
		
	private ClienteRepository clienteRepository;
	private Validator validator;
	private DeletaClienteService deletaClienteService;
	
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.clienteRepository = Mockito.mock(ClienteRepository.class);	
		this.deletaClienteService = new DeletaClienteService(validator, clienteRepository);	
	}
	
	
//	@Test
//	@DisplayName("lança excessão quando cliente não existir.")
//	public void deve_lancar_excessao_quando_cliente_nao_existir() {
//		
//		doThrow(EmptyResultDataAccessException.class).when(clienteRepository).deleteById(543L);
//		
//		 Assertions.assertThrows(ControllerNotFoundException.class,() ->{
//			deletaClienteService.deletaCliente(543l);
//		});
//		
////		assertNotNull(assertThrows);
//		
//		verify(clienteRepository, Mockito.times(1)).deleteById(600L);
//	}
	
	@Test
	@DisplayName("testa delete quando id existir.")
	public void test_delete_quando_id_existir() {
		
		Mockito.doNothing().when(clienteRepository).deleteById(1L);
		
		 Assertions.assertDoesNotThrow(() ->{
			 deletaClienteService.deletaCliente(1L);
		 });	
		verify(clienteRepository, Mockito.times(1)).deleteById(1L);
	}
		
	
}
