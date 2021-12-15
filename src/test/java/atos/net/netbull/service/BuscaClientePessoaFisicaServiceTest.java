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

import atos.net.netbull.repository.ClientePessoaFisicaRepository;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.repository.entity.ClientePessoaFisicaEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class BuscaClientePessoaFisicaServiceTest {

	private Validator validator;
	private ClientePessoaFisicaRepository clienteRepo;
	private BuscaClientePessoaFisicaService buscaClienteServ;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		
		this.clienteRepo = Mockito.mock(ClientePessoaFisicaRepository.class);
		this.buscaClienteServ = new BuscaClientePessoaFisicaService(validator, clienteRepo);	
	}
	
	@Test	
	@DisplayName("Testa Quando encontra um cliente por ID.")
	public void test_quandoEncontraCliente(){
		assertNotNull(buscaClienteServ);
		
		ClientePessoaFisicaEntity clienteEntityTreinado = new ClientePessoaFisicaEntity();
		clienteEntityTreinado.setId(3l);
		
		when(clienteRepo.findById(anyLong()))
				.thenReturn(Optional.of(clienteEntityTreinado));
		
		ClienteEntity notaFiscalVendaEntityRetornado = buscaClienteServ.getClienteById(3l);
		
		then(clienteRepo).should(times(1)).findById(anyLong());
		
		assertNotNull(notaFiscalVendaEntityRetornado);
		assertEquals(3l, notaFiscalVendaEntityRetornado.getId());	
	}
	
	@Test	
	@DisplayName("Testa Quando não encontra cliente por ID.")
	public void test_quandoNaoEncontraClientePorID_lancaExcessao(){
		assertNotNull(this.buscaClienteServ);
		var assertThrows = assertThrows(NotFoundException.class, ()->
			this.buscaClienteServ.getClienteById(3l));
		
		then(clienteRepo).should(times(1)).findById(anyLong());	
		assertEquals(assertThrows.getMessage(), "Cliente não encontrado - id: 3");
		
	}

}
