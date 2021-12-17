package atos.net.netbull.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteInfo;
import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.PessoaFisicaInfo;
import atos.net.netbull.domain.PessoaJuridicaInfo;
import atos.net.netbull.domain.TipoClienteEnum;
import atos.net.netbull.factory.ClienteFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.util.ValidaDocumentos;
import atos.net.netbull.util.ValidaTelefone;

@Service
public class CriaClienteService {
	
	private Validator validator; 
	
	private ClienteRepository repository;
	
	public CriaClienteService(Validator v, ClienteRepository r) {
		this.validator = v;
		this.repository = r;
	}
	
	public ClienteVO persistir(ClienteVO cliente) {
		Set<ConstraintViolation<ClienteVO>> validateMessages = Collections.emptySet();
		
		if(cliente == null) {
			throw new IllegalArgumentException("Cliente nulo");
		}
		
		if(cliente.getTipo() == null) {
			validateMessages = this.validator.validate(cliente, ClienteInfo.class);
			if(!validateMessages.isEmpty()) {
				throw new ConstraintViolationException("Cliente Inválido", validateMessages);
			}
		}
		
		if(cliente.getTipo().equals(TipoClienteEnum.PF)){
			validateMessages = this.validator.validate(cliente, PessoaFisicaInfo.class);
			
			if(!validateMessages.isEmpty()) {
				throw new ConstraintViolationException("Cliente Inválido", validateMessages);
			}
			
			LocalDate idadeMin = LocalDate.now().minus(Period.ofYears(12));
			if(cliente.getDtNascimento().isAfter(idadeMin)) {
				throw new BadRequestException("A idade minima para cadastro de cliente é de 12 anos");
			}
			
			ValidaDocumentos validaDocs = new ValidaDocumentos();			
			if(!validaDocs.isCPF(cliente.getCpf())) {
				throw new BadRequestException("O CPF informado não é válido");
			}
		}else {
			validateMessages = this.validator.validate(cliente, PessoaJuridicaInfo.class);

			if(!validateMessages.isEmpty()) {
				throw new ConstraintViolationException("Cliente Inválido", validateMessages);
			}
			
			ValidaDocumentos validaDocs = new ValidaDocumentos();			
			if(!validaDocs.isCNPJ(cliente.getCnpj())) {
				throw new BadRequestException("O CNPJ informado não é válido");
			}
		}
			
		ValidaTelefone validaTel = new ValidaTelefone();		
		if(!validaTel.telefoneValido(cliente.getTelefone())) {
			throw new BadRequestException("O telefone informado não é válido");
		}
		
		cliente.setDtCriacao(LocalDateTime.now());
		
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();
		
		clienteEntity = repository.save(clienteEntity);	
		
		return  new ClienteFactory(clienteEntity).toVO(); 
	}

}
