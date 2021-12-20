package atos.net.netbull.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;
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
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoEnderecoEnum;
import atos.net.netbull.repository.EnderecoRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class AdicionaEnderecoServiceTest {

	private Validator validator;
	private AdicionaEnderecoService addEnderecoServ;
	private EnderecoRepository enderecoRepo;
	private BuscaClienteService buscaCliente;

	@BeforeAll
	public void inicioGeral() {
		ValidatorFactory validatorFactor = Validation.buildDefaultValidatorFactory();

		this.validator = validatorFactor.getValidator();
	}

	@BeforeEach
	public void iniciarCadaTeste() {
		this.enderecoRepo = Mockito.mock(EnderecoRepository.class);
		this.buscaCliente = Mockito.mock(BuscaClienteService.class);
		this.addEnderecoServ = new AdicionaEnderecoService(this.validator, this.enderecoRepo, this.buscaCliente);
	}

	@Test
	@DisplayName("Testa quando o Endereço é nulo.")
	public void test_quando_endereco_Eh_Null_LancarExcecao() {
		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = null;

		var assertThrows = assertThrows(IllegalArgumentException.class, () -> addEnderecoServ.persistir(endereco));

		assertNotNull(assertThrows);
	}

	@Test
	@DisplayName("Testa os campos do endereço com obrigatoriedade.")
	public void test_quando_TodosCamposObrigatorios_Eh_Null_LancarExcecao() {
		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = new EnderecoVO();

		var assertThrows = assertThrows(ConstraintViolationException.class, () -> addEnderecoServ.persistir(endereco));

		assertEquals(7, assertThrows.getConstraintViolations().size());
		List<String> mensagens = assertThrows.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		assertThat(mensagens,
				hasItems("Campo logradouro não pode ser nulo", "Campo numero não pode ser nulo",
						"Campo bairro não pode ser nulo", "Campo cidade não pode ser nulo",
						"Campo estado não pode ser nulo", "Campo CEP não pode ser nulo"));
	}

	@Test
	@DisplayName("Testa se o CEP informado é valido")
	public void test_quando_CEP_eh_invalido_LancaExcecao() {

		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = new EnderecoVO();
		endereco.setClienteId(1l);
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("1525301288");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		var assertThrows = assertThrows(BadRequestException.class, () -> addEnderecoServ.persistir(endereco));

		assertEquals(assertThrows.getMessage(), "O CEP informado não é válido");
	}

	@Test
	@DisplayName("Testa se o cliente existe")
	public void test_quando_ClientePfNaoExiste_LancaExcessao() {
		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = new EnderecoVO();
		endereco.setClienteId(3l);
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);

		when(buscaCliente.porId(anyLong())).thenReturn(null);
		
		var assertThrows = assertThrows(NotFoundException.class, () -> addEnderecoServ.persistir(endereco));

		then(enderecoRepo).should(times(0)).save(any());

		assertEquals(assertThrows.getMessage(), "O cliente não foi encontrado");
	}

	@Test
	@DisplayName("Testa persistencia da adição de endereço")
	public void test_quando_endereco_adicionado() {
		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = new EnderecoVO();
		endereco.setClienteId(1l);
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		List<EnderecoVO> enderecoTreinado = new ArrayList<EnderecoVO>();
		enderecoTreinado.add(new EnderecoVO(1, 1l));

		ClienteVO clienteTreinado = new ClienteVO(1l, enderecoTreinado);

		when(buscaCliente.porId(anyLong())).thenReturn(clienteTreinado);

		EnderecoVO endCriado = addEnderecoServ.persistir(endereco);

		then(buscaCliente).should(times(1)).porId(anyLong());

		assertNotNull(endCriado);

	}
	
	@Test
	@DisplayName("Testa quando a quantidade limite de endereços")
	public void test_quando_qtd_max_enderecos_atingido() {
		assertNotNull(addEnderecoServ);

		EnderecoVO endereco = new EnderecoVO();
		endereco.setClienteId(1l);
		endereco.setLogradouro("Rua Genérica");
		endereco.setNumero("1526");
		endereco.setBairro("Jardim Genérico");
		endereco.setCidade("Cidade Genérica");
		endereco.setEstado("SP");
		endereco.setCep("15253012");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		List<EnderecoVO> enderecoTreinado = new ArrayList<EnderecoVO>();
		enderecoTreinado.add(new EnderecoVO(1, 1l));
		enderecoTreinado.add(new EnderecoVO(2, 1l));
		enderecoTreinado.add(new EnderecoVO(3, 1l));

		ClienteVO clienteTreinado = new ClienteVO(1l, enderecoTreinado);

		when(buscaCliente.porId(anyLong())).thenReturn(clienteTreinado);

		var assertThrows = assertThrows(BadRequestException.class, () -> addEnderecoServ.persistir(endereco));
		
		then(buscaCliente).should(times(1)).porId(anyLong());
		
		assertEquals(assertThrows.getMessage(), "A quantidade máxima de endereços foi atingida");
	}
}
