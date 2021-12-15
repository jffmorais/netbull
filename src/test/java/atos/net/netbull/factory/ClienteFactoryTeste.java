package atos.net.netbull.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoEnderecoEnum;
import atos.net.netbull.repository.entity.ClienteEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ClienteFactoryTeste {
	
	@Test
	@DisplayName("Testa o factory de VO para entity")
	public void test_quandoCriarEntityPeloVO_sohTransformaparaEntity() {
		
		ClienteVO cliente = new ClienteVO();
		
		cliente.setNome("Marcos Nascimento");
		cliente.setCpf("12345678911");
		cliente.setDtNascimento(LocalDate.now());
		cliente.setDtCriacao(LocalDateTime.now());
		cliente.setEmail("marcos@gmail.com");
		cliente.setTelefone("11999999999");
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setLogradouro("av paulista");
		endereco.setNumero(500);
		endereco.setComplemento("Masp");
		endereco.setBairro("Bela vista");
		endereco.setEstado("São Paulo");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		cliente.add(endereco);
		
		ClienteEntity entityCriado = new ClienteFactory(cliente).toEntity();
		
		assertNotNull(entityCriado);
		assertNotNull(entityCriado.getNome());
		assertEquals(cliente.getNome(),entityCriado.getNome());
		
		assertNotNull(entityCriado.getCpf());
		assertEquals(cliente.getCpf(),entityCriado.getCpf());
		
		assertNotNull(entityCriado.getDtNascimento());
		assertEquals(cliente.getDtNascimento(),entityCriado.getDtNascimento());
		
		assertNotNull(entityCriado.getDtCriacao());
		assertEquals(cliente.getDtCriacao(),entityCriado.getDtCriacao());
		
		assertNotNull(entityCriado.getEmail());
		assertEquals(cliente.getEmail(),entityCriado.getEmail());
		
		assertNotNull(entityCriado.getTelefone());
		assertEquals(cliente.getTelefone(),entityCriado.getTelefone());
		
		assertNotNull(entityCriado.getEnderecos());
		assertEquals(cliente.getEnderecos().size(),entityCriado.getEnderecos().size());
		
		assertEquals(cliente.getEnderecos().get(0).getLogradouro(),entityCriado.getEnderecos().get(0).getLogradouro());
		assertEquals(cliente.getEnderecos().get(0).getNumero(),entityCriado.getEnderecos().get(0).getNumero());
		assertEquals(cliente.getEnderecos().get(0).getComplemento(),entityCriado.getEnderecos().get(0).getComplemento());
		assertEquals(cliente.getEnderecos().get(0).getBairro(),entityCriado.getEnderecos().get(0).getBairro());
		assertEquals(cliente.getEnderecos().get(0).getEstado(),entityCriado.getEnderecos().get(0).getEstado());
		assertEquals(cliente.getEnderecos().get(0).getCep(),entityCriado.getEnderecos().get(0).getCep());
		assertEquals(cliente.getEnderecos().get(0).getTipo(),entityCriado.getEnderecos().get(0).getTipo());
		
		
		ClienteVO voCriado = new ClienteFactory(entityCriado).toVO();
		
		assertNotNull(voCriado);
		assertNotNull(voCriado.getNome());
		assertEquals(cliente.getNome(),voCriado.getNome());
		
		assertNotNull(voCriado.getCpf());
		assertEquals(cliente.getCpf(),voCriado.getCpf());
		
		assertNotNull(voCriado.getDtNascimento());
		assertEquals(cliente.getDtNascimento(),voCriado.getDtNascimento());
		
		assertNotNull(voCriado.getEmail());
		assertEquals(cliente.getEmail(),voCriado.getEmail());
		
		assertNotNull(voCriado.getTelefone());
		assertEquals(cliente.getTelefone(),voCriado.getTelefone());
		
		assertNotNull(voCriado.getEnderecos());
		assertEquals(cliente.getEnderecos().size(),voCriado.getEnderecos().size());
		
		assertEquals(cliente.getEnderecos().get(0).getLogradouro(),voCriado.getEnderecos().get(0).getLogradouro());
		assertEquals(cliente.getEnderecos().get(0).getNumero(),voCriado.getEnderecos().get(0).getNumero());
		assertEquals(cliente.getEnderecos().get(0).getComplemento(),voCriado.getEnderecos().get(0).getComplemento());
		assertEquals(cliente.getEnderecos().get(0).getBairro(),voCriado.getEnderecos().get(0).getBairro());
		assertEquals(cliente.getEnderecos().get(0).getEstado(),voCriado.getEnderecos().get(0).getEstado());
		assertEquals(cliente.getEnderecos().get(0).getCep(),voCriado.getEnderecos().get(0).getCep());
		assertEquals(cliente.getEnderecos().get(0).getTipo(),voCriado.getEnderecos().get(0).getTipo());
	}
}
