package atos.net.netbull.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

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
import atos.net.netbull.domain.TipoClienteEnum;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuscaClienteServiceTeste {

	private BuscaClienteService buscaClienteService;
	
	private Validator validator;
	
	private ClienteRepository clienteRepository;
	
	
	@BeforeAll
	public void inicioGeral() {
		
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.clienteRepository = Mockito.mock(ClienteRepository.class);
		
		buscaClienteService = new BuscaClienteService(validator, clienteRepository);	
	}
	
	@Test	
	@DisplayName("Testa Quando encontra um cliente por ID.")
	public void test_quandoEncontraCliente(){
		assertNotNull(buscaClienteService);
		
		ClienteEntity clienteEntityTreinado = new ClienteEntity();
		clienteEntityTreinado.setId(3l);
		clienteEntityTreinado.setTipo(TipoClienteEnum.PF);
		
		when(clienteRepository.findById(anyLong()))
				.thenReturn(Optional.of(clienteEntityTreinado));
		
		ClienteVO notaFiscalVendaEntityRetornado = buscaClienteService.porId(3l);
		
		then(clienteRepository).should(times(1)).findById(anyLong());
		
		assertNotNull(notaFiscalVendaEntityRetornado);
		assertEquals(3l, notaFiscalVendaEntityRetornado.getId());
		
		
	}
	
	@Test	
	@DisplayName("Testa Quando não encontra cliente por ID.")
	public void test_quandoNaoEncontraClientePorID_lancaExcessao(){
		assertNotNull(this.buscaClienteService);
		var assertThrows = assertThrows(NotFoundException.class, ()->
			this.buscaClienteService.porId(3l));
		
		then(clienteRepository).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Cliente não encontrado - id: 3");
		
	}

}
