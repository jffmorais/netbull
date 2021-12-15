package atos.net.netbull.service;

import org.springframework.stereotype.Service;

import atos.net.netbull.repository.EnderecoRepository;

@Service
public class DeletaEnderecoService {

	private EnderecoRepository endRepo;

	public DeletaEnderecoService(EnderecoRepository endRepo) {
		super();
		this.endRepo = endRepo;
	}
	
	public void excluiEndereco(Long id, Integer endNum) {
		endRepo.deletaEndereco(id, endNum);
	}
}
