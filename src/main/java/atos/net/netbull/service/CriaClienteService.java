package atos.net.netbull.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
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
	
	public void persistir(ClienteVO cliente) {
		Set<ConstraintViolation<ClienteVO>> validateMessages = this.validator.validate(cliente);
		
		if(!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validateMessages);
		}
	}

//	public ClienteVO findById(long id) {
//		Optional<ClienteEntity> obj = repository.findById(id);
//		ClienteEntity entidade = obj.orElseThrow(()-> new NotFoundException("cliente nao encontrado"));
//		return new ClienteVO(entidade);
//	}
}
