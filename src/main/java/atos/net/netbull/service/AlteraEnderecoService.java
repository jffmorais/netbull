package atos.net.netbull.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;

@Service
public class AlteraEnderecoService {

	private Validator validator;
	private ClienteRepository repository;
	
	public AlteraEnderecoService(Validator validator, ClienteRepository repository) {
		super();
		this.validator = validator;
		this.repository = repository;
	}

	public ClienteEntity persistir(ClienteEntity clienteEntity) {
		Set<ConstraintViolation<ClienteEntity>> validateMessages = this.validator.validate(clienteEntity);
		
		ClienteEntity clienteAlterado = null;
		
		Optional<ClienteEntity> clienteExistente = this.repository.findById(clienteEntity.getId());
		if(clienteExistente.isPresent()) {
			clienteAlterado = this.repository.save(clienteEntity);
		}else {
			throw new NotFoundException("Cliente não encontrado");
		}
		
		return clienteAlterado;
	}
	
	
}
