package atos.net.netbull.service;

import java.util.List;

import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atos.net.netbull.repository.EnderecoRepository;

@Service
public class DeletaEnderecoService {

	private EnderecoRepository endRepo;
	private Validator validador;

	public DeletaEnderecoService(Validator validador, EnderecoRepository endRepo) {
		super();
		this.validador = validador;
		this.endRepo = endRepo;
	}
	
	@Transactional
	public void excluiEndereco(Long id, Integer endNum) {
		
		List<Integer> nrEnderecos = endRepo.buscaQuantidadeEnderecosCliente(id);
		
		if(nrEnderecos.size() > 1) {
			if(nrEnderecos.contains(endNum)) {
				endRepo.deletaEndereco(id, endNum);
			}else {
				throw new NotFoundException("Endereço não consta na base");
			}	
		}else {
			throw new BadRequestException("Ao menos um endereço deve estar cadastrado");	
		}	
	}
}
