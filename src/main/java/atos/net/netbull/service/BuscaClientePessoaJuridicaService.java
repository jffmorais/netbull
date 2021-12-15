package atos.net.netbull.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.repository.ClientePessoaJuridicaRepository;
import atos.net.netbull.repository.entity.ClientePessoaJuridicaEntity;

@Service
public class BuscaClientePessoaJuridicaService {

	private Validator validator;
	private ClientePessoaJuridicaRepository clienteRepo;
	
	public BuscaClientePessoaJuridicaService(Validator validator, ClientePessoaJuridicaRepository clienteRepo) {
		super();
		this.validator = validator;
		this.clienteRepo = clienteRepo;
	}
	
	public ClientePessoaJuridicaEntity getClienteById(long id) {
		return this.clienteRepo.findById(id)
				.orElseThrow(()-> new NotFoundException("Cliente não encontrado - id: " + id));
	}
}
