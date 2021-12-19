package atos.net.netbull.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.domain.TipoClienteEnum;
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
		cliVO.setTipo(pEntity.getTipo());
		cliVO.setDtCriacao(pEntity.getDtCriacao());
		cliVO.setEmail(pEntity.getEmail());
		cliVO.setTelefone(pEntity.getTelefone());
		
		if (pEntity.getTipo().equals(TipoClienteEnum.PF)) {
			cliVO.setNome(pEntity.getNome());
			cliVO.setCpf(pEntity.getCpf());
			cliVO.setDtNascimento(pEntity.getDtNascimento());
		} else {
			cliVO.setCnpj(pEntity.getCnpj());
			cliVO.setRazaoSocial(pEntity.getRazaoSocial());
		}

		AtomicInteger numeroEndereco = new AtomicInteger();
		List<EnderecoEntity> enderecos = Optional.ofNullable(pEntity.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco -> this.construirEnderecoVO(cliVO, numeroEndereco, endereco));

		return cliVO;
	}

	private void construirEnderecoVO(ClienteVO cliVO, AtomicInteger numeroEndereco, EnderecoEntity endereco) {
		EnderecoVO enderecoVO = new EnderecoVO();

		enderecoVO.setId(endereco.getId().getNumeroEndereco());
		enderecoVO.setClienteId(endereco.getId().getCliente().getId());
		enderecoVO.setLogradouro(endereco.getLogradouro());
		enderecoVO.setNumero(endereco.getNumero());
		enderecoVO.setComplemento(endereco.getComplemento());
		enderecoVO.setBairro(endereco.getBairro());
		enderecoVO.setCidade(endereco.getCidade());
		enderecoVO.setEstado(endereco.getEstado());
		enderecoVO.setCep(endereco.getCep());
		enderecoVO.setTipo(endereco.getTipo());

		cliVO.addEndereco(enderecoVO);

	}

	private ClienteEntity transformaEntity(ClienteVO cliente) {
		ClienteEntity clienteEntity = new ClienteEntity();

		if(cliente.getId() != null) {
			clienteEntity.setId(cliente.getId());
		}
		clienteEntity.setTipo(cliente.getTipo());
		clienteEntity.setTelefone(cliente.getTelefone());
		clienteEntity.setDtCriacao(cliente.getDtCriacao());
		clienteEntity.setEmail(cliente.getEmail());
		
		if(cliente.getTipo().equals(TipoClienteEnum.PF)) {
			clienteEntity.setNome(cliente.getNome());
			clienteEntity.setCpf(cliente.getCpf());
			clienteEntity.setDtNascimento(cliente.getDtNascimento());
		}else {
			clienteEntity.setCnpj(cliente.getCnpj());
			clienteEntity.setRazaoSocial(cliente.getRazaoSocial());
		}

		AtomicInteger numeroEndereco = new AtomicInteger();
		List<EnderecoVO> enderecos = Optional.ofNullable(cliente.getEnderecos()).orElseGet(ArrayList::new);
		enderecos.stream().forEach(endereco -> this.construirEnderecoEntity(clienteEntity, numeroEndereco, endereco));
		return clienteEntity;

	}

	private void construirEnderecoEntity(ClienteEntity clienteEntity, AtomicInteger numeroEndereco,
			EnderecoVO endereco) {

		EnderecoEntity enderecoEntity = new EnderecoEntity();
		enderecoEntity.setId(new EnderecoPK());
		enderecoEntity.getId().setNumeroEndereco(numeroEndereco.incrementAndGet());
		enderecoEntity.getId().setCliente(clienteEntity);
		enderecoEntity.setLogradouro(endereco.getLogradouro());
		enderecoEntity.setComplemento(endereco.getComplemento());
		enderecoEntity.setNumero(endereco.getNumero());
		enderecoEntity.setBairro(endereco.getBairro());
		enderecoEntity.setCidade(endereco.getCidade());
		enderecoEntity.setCep(endereco.getCep());
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