package atos.net.netbull.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClientePessoaFisicaRepository;
import atos.net.netbull.repository.entity.ClientePessoaFisicaEntity;

@Service
public class BuscaClientePessoaFisicaService {
	
	private Validator validator;
	private ClientePessoaFisicaRepository clienteRepo;
	
	public BuscaClientePessoaFisicaService(Validator validator, ClientePessoaFisicaRepository clienteRepo) {
		super();
		this.validator = validator;
		this.clienteRepo = clienteRepo;
	}
	
	public ClientePessoaFisicaEntity getClienteById(long id) {
		return this.clienteRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente não encontrado - id: " + id));
	}

}
