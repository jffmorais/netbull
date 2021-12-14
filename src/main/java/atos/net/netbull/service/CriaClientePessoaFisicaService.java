package atos.net.netbull.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.PessoaFisicaInfo;
import atos.net.netbull.repository.ClientePessoaFisicaRepository;
import atos.net.netbull.util.ValidaDocumentos;
import atos.net.netbull.util.ValidaTelefone;

@Service
public class CriaClientePessoaFisicaService {
	
	private Validator validador;
	private ClientePessoaFisicaRepository clienteRepo;

	public CriaClientePessoaFisicaService(Validator validador, ClientePessoaFisicaRepository clienteRepo) {
		super();
		this.validador = validador;
		this.clienteRepo = clienteRepo;
	}
	
	public void persistir(@NotNull ClienteVO cliente) {
		
		Set<ConstraintViolation<ClienteVO>> validateMessages = this.validador.validate(cliente, PessoaFisicaInfo.class);
		
		if(!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Cliente Pessoa Física Inválido", validateMessages);
		}
		
		LocalDate idadeMin = LocalDate.now().minus(Period.ofYears(12));
		if(cliente.getDtNascimento().isAfter(idadeMin)) {
			throw new BadRequestException("A idade minima para cadastro de cliente é de 12 anos");
		}
		
		ValidaDocumentos validaDocs = new ValidaDocumentos();
		
		if(!validaDocs.isCPF(cliente.getCpf())) {
			throw new BadRequestException("O CPF informado não é válido");
		}
		
		ValidaTelefone validaTel = new ValidaTelefone();
		if(!validaTel.telefoneValido(cliente.getTelefone())) {
			throw new BadRequestException("O telefone informado não é válido");
		}
		
	}

}
