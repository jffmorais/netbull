package atos.net.netbull.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoEnderecoEnum;

import atos.net.netbull.repository.entity.EnderecoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EnderecoFactoryTeste {
	
	@Test
	@DisplayName("Testa o CleinteFactory de VO para entity")
	public void test_quandoCriarEntityPeloVO_sohTransformaparaEntity() {
		
		EnderecoVO  endereco = new EnderecoVO();
		
		
		endereco.setLogradouro("av paulista");
		endereco.setNumero("500");
		endereco.setComplemento("Masp");
		endereco.setBairro("Bela vista");
		endereco.setEstado("São Paulo");
		endereco.setCep("01311-000");
		endereco.setTipo(TipoEnderecoEnum.RESIDENCIA);
		
		EnderecoEntity entityCriado = new EnderecoFactory(endereco).toEntity();
		
		assertNotNull(entityCriado);
		assertNotNull(entityCriado.getLogradouro());
		assertEquals(endereco.getLogradouro(),entityCriado.getLogradouro());
		
		assertNotNull(entityCriado.getNumero());
		assertEquals(endereco.getNumero(),entityCriado.getNumero());
		
		assertNotNull(entityCriado.getComplemento());
		assertEquals(endereco.getComplemento(),entityCriado.getComplemento());
		
		assertNotNull(entityCriado.getBairro());
		assertEquals(endereco.getBairro(),entityCriado.getBairro());
		
		assertNotNull(entityCriado.getEstado());
		assertEquals(endereco.getEstado(),entityCriado.getEstado());
		
		assertNotNull(entityCriado.getCep());
		assertEquals(endereco.getCep(),entityCriado.getCep());
		
		assertNotNull(entityCriado.getTipo());
		assertEquals(endereco.getTipo(),entityCriado.getTipo());
		
		EnderecoVO voCriado = new EnderecoFactory(entityCriado).toVO();
		
		assertNotNull(voCriado);
		assertNotNull(voCriado.getLogradouro());
		assertEquals(voCriado.getLogradouro(),voCriado.getLogradouro());
		
		assertNotNull(voCriado.getNumero());
		assertEquals(endereco.getNumero(),voCriado.getNumero());
		
		assertNotNull(voCriado.getComplemento());
		assertEquals(endereco.getComplemento(),voCriado.getComplemento());
		
		assertNotNull(voCriado.getBairro());
		assertEquals(endereco.getBairro(),voCriado.getBairro());
		
		assertNotNull(voCriado.getEstado());
		assertEquals(endereco.getEstado(),voCriado.getEstado());
		
		assertNotNull(voCriado.getCep());
		assertEquals(endereco.getCep(),voCriado.getCep());
		
		assertNotNull(voCriado.getTipo());
		assertEquals(endereco.getTipo(),voCriado.getTipo());
	}
}
