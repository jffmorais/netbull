package atos.net.netbull.service;

import static org.junit.jupiter.api.Assertions.*;
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
class BuscaClienteServiceTest {

	private Validator validator;
	private ClienteRepository clienteRepo;
	private BuscaClienteService buscaClienteServ;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.clienteRepo = Mockito.mock(ClienteRepository.class);
		this.buscaClienteServ = new BuscaClienteService(validator, clienteRepo);	
	}
	
	@Test	
	@DisplayName("Testa Quando encontra um cliente por ID.")
	public void test_quandoEncontraCliente(){
		assertNotNull(buscaClienteServ);
		
		ClienteEntity clienteEntityTreinado = new ClienteEntity();
		clienteEntityTreinado.setId(3l);
		clienteEntityTreinado.setTipo(TipoClienteEnum.PF);
		
		when(clienteRepo.findById(anyLong()))
				.thenReturn(Optional.of(clienteEntityTreinado));
		
		ClienteVO clienteRetornado = buscaClienteServ.porId(3l);
		
		then(clienteRepo).should(times(1)).findById(anyLong());
		
		assertNotNull(clienteRetornado);
		assertEquals(3l, clienteRetornado.getId());	
	}
	
	@Test	
	@DisplayName("Testa Quando não encontra cliente por ID.")
	public void test_quandoNaoEncontraClientePorID_lancaExcessao(){
		assertNotNull(this.buscaClienteServ);
		var assertThrows = assertThrows(NotFoundException.class, ()->
			this.buscaClienteServ.porId(3l));
		
		then(clienteRepo).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Cliente não encontrado - id: 3");
		
	}

}
