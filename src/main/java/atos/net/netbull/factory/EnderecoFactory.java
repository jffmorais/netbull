package atos.net.netbull.factory;

import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.repository.entity.EnderecoPK;

public class EnderecoFactory {

	private EnderecoVO vo;
	private EnderecoEntity entity;
	
	public EnderecoFactory(EnderecoVO vo) {
		this.vo = vo;
		this.entity = transformaEntity(vo);
	}
	
	public EnderecoFactory(EnderecoEntity entity) {
		this.vo = this.transformaVO(entity);
		this.entity = entity;
	}
	
	private EnderecoVO transformaVO(EnderecoEntity entity) {
		EnderecoVO  endVO = new EnderecoVO();
		endVO.setId(entity.getId().getNumeroEndereco());
		endVO.setClienteId(entity.getId().getCliente().getId());
		endVO.setLogradouro(entity.getLogradouro());
		endVO.setNumero(entity.getNumero());
		endVO.setComplemento(entity.getComplemento());
		endVO.setBairro(entity.getBairro());
		endVO.setCidade(entity.getCidade());
		endVO.setEstado(entity.getEstado());
		endVO.setCep(entity.getCep());
		endVO.setTipo(entity.getTipo());
		return endVO;
	}
	
	private EnderecoEntity transformaEntity(EnderecoVO vo) {
		EnderecoEntity entity = new EnderecoEntity();
		
		ClienteEntity cliente = new ClienteEntity();
		cliente.setId(vo.getClienteId());
		
		EnderecoPK endPK = new EnderecoPK();
		endPK.setCliente(cliente);
		endPK.setNumeroEndereco(vo.getId());
		
		entity.setId(endPK);
		entity.setLogradouro(vo.getLogradouro());
		entity.setNumero(vo.getNumero());
		entity.setComplemento(vo.getComplemento());
		entity.setBairro(vo.getBairro());
		entity.setCidade(vo.getCidade());
		entity.setEstado(vo.getEstado());
		entity.setCep(vo.getCep());
		entity.setTipo(vo.getTipo());
		return entity;
	}
	
	public EnderecoEntity toEntity() {
		return this.entity;
	}
	
	public EnderecoVO toVO() {
		return this.vo;
	}
}
