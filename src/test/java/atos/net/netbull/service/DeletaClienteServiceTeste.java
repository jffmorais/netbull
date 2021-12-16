package atos.net.netbull.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

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
public class DeletaClienteServiceTeste {
	
	
	
	
	private ClienteRepository clienteRepository;
	
	private DeletaClienteService deletaClienteService;
	
	
	@BeforeAll
	public void inicioGeral() {
		
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		
	}
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.clienteRepository = Mockito.mock(ClienteRepository.class);
		
		deletaClienteService = new DeletaClienteService( clienteRepository);	
	}
	
	
	@Test
	@DisplayName("lança excessão quando cliente não existir.")
	public void deve_lancar_excessao_quando_cliente_nao_existir() {
		
		doThrow(NotFoundException.class).when(clienteRepository).deleteById(Long.valueOf(55l));
		
		var assertThrows = assertThrows(NotFoundException.class,() ->{
			deletaClienteService.deletaCliente(Long.valueOf(55l));
		});
		
		assertNotNull(assertThrows);
	}
	

		
	
}
