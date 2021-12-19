package atos.net.netbull.service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.service.exceptions.ControllerNotFoundException;

@Service
public class AtualizaClienteService {
	
	private ClienteRepository clienteRepository;

	public AtualizaClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public ClienteVO update(Long id, ClienteVO vo) {
		try { 
		ClienteEntity entity = clienteRepository.getById(id);
		
		if(entity.toString() == null) {
			throw new NotFoundException();
		}
		ClienteEntity clienteEntity = new ClienteFactory(vo).toEntity();
		
		entity.setNome(clienteEntity.getNome());
		entity.setCpf(clienteEntity.getCpf());
		entity.setEmail(clienteEntity.getEmail());
		entity.setTelefone(clienteEntity.getTelefone());
		entity.setDtNascimento(clienteEntity.getDtNascimento());
	
		entity = clienteRepository.save(entity);
		return new ClienteFactory(entity).toVO();
		}
		catch(EntityNotFoundException e) {
			throw new ControllerNotFoundException("Id não encontrado " + id);
		}
		
	}
	
	
}
