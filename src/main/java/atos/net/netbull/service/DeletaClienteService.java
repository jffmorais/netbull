package atos.net.netbull.service;



import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClienteRepository;


@Service
public class DeletaClienteService {
	
	private ClienteRepository clienteRepository;
	
	public DeletaClienteService( ClienteRepository repository) {
			
		this.clienteRepository = repository; 	
	}
	
	
//	public ResponseEntity<?> deletaCliente(Long id) {
//		Optional<ClienteEntity> optional = clienteRepository.findById(id);
//		if(optional.isPresent()) {
//			
//			clienteRepository.deleteById(id);
//			
//			}
//		
//			
//		
//		return new EmptyResultDataAccessException();
//			
//		
//	}
	
	
	public void deletaCliente(Long id) throws Exception {
		
		try {
			clienteRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new BadRequestException();
		}
	}
}
