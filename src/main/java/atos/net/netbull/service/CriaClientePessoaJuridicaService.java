package atos.net.netbull.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.PessoaJuridicaInfo;

@Service
public class CriaClientePessoaJuridicaService {
	
	private Validator validador;

	public CriaClientePessoaJuridicaService(Validator validador) {
		super();
		this.validador = validador;
	}
	
	public void persistir(@NotNull ClienteVO cliente) {
		Set<ConstraintViolation<ClienteVO>> validateMessages = this.validador.validate(cliente, PessoaJuridicaInfo.class);
		
		if(!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Cliente Pessoa Juridica Inválido", validateMessages);
		}
		
	}

}
