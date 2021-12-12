package atos.net.netbull.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;

@Service
public class CriaClienteService {
	
	private Validator validator; 
	
	public CriaClienteService(Validator v) {
		this.validator = v;
	}
	
	public void persistir(ClienteVO cliente) {
		Set<ConstraintViolation<ClienteVO>> validateMessages = this.validator.validate(cliente);
		
		if(!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Cliente Inválido", validateMessages);
		}
	}
}
