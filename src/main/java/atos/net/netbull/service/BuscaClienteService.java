package atos.net.netbull.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.factory.ClienteFactory;
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

		
	public ClienteVO porId(long id) {
	 ClienteEntity clienteEntity =	this.repositoy.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente não encontrado "+id));	
		
		return new ClienteFactory(clienteEntity).toVO(); 
	}


	public Page<ClienteVO> findAllPaged(Pageable pageable) {
		Page<ClienteEntity> lista = repositoy.findAll(pageable);
		return lista.map(x -> new ClienteFactory(x).toVO());
	}
	
}
