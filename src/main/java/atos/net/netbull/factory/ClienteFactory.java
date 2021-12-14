package atos.net.netbull.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.repository.entity.EnderecoPK;

public class ClienteFactory {
	
	private ClienteVO vo;
	private ClienteEntity entity;
	
	public ClienteFactory(ClienteVO pVo) {
		this.entity = this.transformaEntity(pVo);
		this.vo = pVo;		
	}
	
	public ClienteFactory(ClienteEntity pEntity) {
		this.entity = pEntity;
		this.vo = this.transformaVO(pEntity);		
	}
	
	public ClienteVO transformaVO(ClienteEntity pEntity) {
		ClienteVO cliVO = new ClienteVO();
		
		cliVO.setId(pEntity.getId());
		cliVO.setNome( pEntity.getNome());
		cliVO.setCpf( pEntity.getCpf());
		cliVO.setDtNascimento( pEntity.getDtNascimento());
		cliVO.setDtCriacao( pEntity.getDtCriacao());
		cliVO.setEmail(pEntity.getEmail());
		cliVO.setTelefone( pEntity.getTelefone());
		
		AtomicInteger numeroEndereco = new AtomicInteger();
		List<EnderecoEntity> enderecos =  Optional.ofNullable(pEntity.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco ->
				this.construirEnderecoVO(cliVO, numeroEndereco, endereco));
		
		return cliVO;
	}

	private void construirEnderecoVO(ClienteVO cliVO, AtomicInteger numeroEndereco, EnderecoEntity endereco) {
		EnderecoVO enderecoVO = new EnderecoVO();
		
		
		enderecoVO.setLogradouro( endereco.getLogradouro());
		enderecoVO.setNumero(endereco.getNumero());
		enderecoVO.setComplemento( endereco.getComplemento());
		enderecoVO.setBairro( endereco.getBairro());
		enderecoVO.setCidade( endereco.getCidade());
		enderecoVO.setEstado(endereco.getEstado());
		enderecoVO.setCEP( endereco.getCep());
		enderecoVO.setTipo( endereco.getTipo());
		
		cliVO.add(enderecoVO);
		
	}
	
	private ClienteEntity transformaEntity(ClienteVO cliente) {
		ClienteEntity clienteEntity = new ClienteEntity();
		
		clienteEntity.setId(cliente.getId());
		clienteEntity.setNome(cliente.getNome());
		clienteEntity.setTelefone(cliente.getTelefone());
		clienteEntity.setCpf(cliente.getCpf());
		clienteEntity.setDtCriacao(cliente.getDtCriacao());
		clienteEntity.setDtNascimento(cliente.getDtNascimento());
		clienteEntity.setEmail(cliente.getEmail());
		
		AtomicInteger numeroEndereco = new AtomicInteger();
		List<EnderecoVO> enderecos = Optional.ofNullable(cliente.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco ->
				this.construirEnderecoEntity(clienteEntity, numeroEndereco, endereco));
		return clienteEntity;
		
	}

	private void construirEnderecoEntity(ClienteEntity clienteEntity, AtomicInteger numeroEndereco,EnderecoVO endereco) {
		
		EnderecoEntity enderecoEntity = new EnderecoEntity();
		enderecoEntity.setId(new EnderecoPK());
		enderecoEntity.getId().setNumeroEndereco(numeroEndereco.incrementAndGet());
		enderecoEntity.getId().setCliente(clienteEntity);
		enderecoEntity.setLogradouro(endereco.getLogradouro());
		enderecoEntity.setComplemento(endereco.getComplemento());
		enderecoEntity.setNumero(endereco.getNumero());
		enderecoEntity.setBairro(endereco.getBairro());
		enderecoEntity.setCidade(endereco.getCidade());
		enderecoEntity.setCep(endereco.getCEP());
		enderecoEntity.setEstado(endereco.getEstado());
		enderecoEntity.setTipo(endereco.getTipo());
		enderecoEntity.setBairro(endereco.getBairro());
		
		
		clienteEntity.add(enderecoEntity);
	}
	
	public ClienteEntity toEntity() {		
		return this.entity;
	}

	public ClienteVO toVO() {
		
		return this.vo;
	}
}
