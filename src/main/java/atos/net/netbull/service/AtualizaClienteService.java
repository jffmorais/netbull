package atos.net.netbull.service;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;

@Service
public class AtualizaClienteService {
	
	private ClienteRepository clienteRepository;

	public AtualizaClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public ClienteVO update(Long id, ClienteVO vo) {
		ClienteEntity entity = clienteRepository.getById(id);
			
		ClienteEntity clienteEntity = new ClienteFactory(vo).toEntity();
		
		entity.setNome(clienteEntity.getNome());
		entity.setCpf(clienteEntity.getCpf());
		entity.setEmail(clienteEntity.getEmail());
		entity.setTelefone(clienteEntity.getTelefone());
		entity.setDtNascimento(clienteEntity.getDtNascimento());
	
		entity = clienteRepository.save(entity);
		return new ClienteFactory(entity).toVO();
		
	}
	
	
}
