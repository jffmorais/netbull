package atos.net.netbull.service;



import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.service.exceptions.ControllerNotFoundException;


@Service
public class DeletaClienteService {
	
	private ClienteRepository clienteRepository;
	
	public DeletaClienteService( ClienteRepository repository) {
			
		this.clienteRepository = repository; 	
	}
	
	

	
	
	public void deletaCliente(Long id) throws Exception {
		
		try {
			clienteRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ControllerNotFoundException("ID não encontrado " + id);
		}
	}
}
