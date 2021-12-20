package atos.net.netbull.service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import atos.net.netbull.repository.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class DeletaEnderecoServiceTest {
	
	private Validator validator;
	private DeletaEnderecoService deletaEnderecoServ;
	private EnderecoRepository enderecoRepo;
	
	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	public void iniciarCadaTeste() {
		//this.deletaEnderecoServ = Mockito.mock(DeletaEnderecoService.class);
		this.deletaEnderecoServ = new DeletaEnderecoService(this.validator, this.enderecoRepo);
	}

	// to-do : testar existencia do endereco e o limite (pelo menos)

}
