package atos.net.netbull.service;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;

@Service
public class CriaClienteService {
	
	private Validator validator; 
	
	private ClienteRepository repository;
	
	public CriaClienteService(Validator v, ClienteRepository r) {
		this.validator = v;
		this.repository = r;
	}
	
	public ClienteVO persistir(ClienteVO cliente) {
		Set<ConstraintViolation<ClienteVO>> validateMessages = this.validator.validate(cliente);
		
		if(!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validateMessages);
		}
		
		cliente.setDtCriacao(LocalDateTime.now());
		
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();
		
		clienteEntity = repository.save(clienteEntity);
		
		
		
		
		return  new ClienteFactory(clienteEntity).toVO(); 
	}

//	public ClienteVO findById(long id) {
//		Optional<ClienteEntity> obj = repository.findById(id);
//		ClienteEntity entidade = obj.orElseThrow(()-> new NotFoundException("cliente nao encontrado"));
//		return new ClienteVO(entidade);
//	}
}
