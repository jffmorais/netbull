package atos.net.netbull.service;

import java.util.Optional;

import javax.validation.Validator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.service.exceptions.ControllerNotFoundException;

@Service
public class BuscaClienteService {
	
    private Validator validator;
	
	private ClienteRepository repositoy;
	
	public BuscaClienteService(Validator v, ClienteRepository repository) {
		this.validator = v;		
		this.repositoy = repository; 	
	}

		
	public ClienteVO porId(long id) {
	 Optional<ClienteEntity> obj =	this.repositoy.findById(id);
	 ClienteEntity entity = obj.orElseThrow(()-> new ControllerNotFoundException("Cliente não encontrado"));	
		
		return new ClienteFactory(entity).toVO(); 
	}


	public Page<ClienteVO> findAllPaged(Pageable pageable) {
		Page<ClienteEntity> lista = repositoy.findAll(pageable);
		return lista.map(x -> new ClienteFactory(x).toVO());
	}
	
}
