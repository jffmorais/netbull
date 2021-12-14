package atos.net.netbull.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;

@Service
public class BuscaClienteService {
	
    private Validator validator;
	
	private ClienteRepository repositoy;
	
	public BuscaClienteService(Validator v, ClienteRepository repository) {
		this.validator = v;		
		this.repositoy = repository; 	
	}

		
	public ClienteEntity porId(long id) {
		return this.repositoy.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente não encontrado "+id));		
	}
	
}
