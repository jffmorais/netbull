package atos.net.netbull.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atos.net.netbull.domain.EnderecoInfo;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.repository.EnderecoRepository;
import atos.net.netbull.repository.entity.ClientePessoaFisicaEntity;
import atos.net.netbull.repository.entity.ClientePessoaJuridicaEntity;
import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.util.ValidaCep;
import factory.EnderecoFactory;

@Service
public class AdicionaEnderecoService {

	private Validator validador;
	private EnderecoRepository enderecoRepo;
	private BuscaClientePessoaFisicaService clientePfServ;
	private BuscaClientePessoaJuridicaService clientePjServ;

	public AdicionaEnderecoService(Validator validador, EnderecoRepository enderecoRepo,
			BuscaClientePessoaFisicaService clientePfServ, BuscaClientePessoaJuridicaService clientePjServ) {
		super();
		this.validador = validador;
		this.enderecoRepo = enderecoRepo;
		this.clientePfServ = clientePfServ;
		this.clientePjServ = clientePjServ;
	}

	@Transactional
	public EnderecoVO persistir(@NotNull EnderecoVO endereco) {
		Set<ConstraintViolation<EnderecoVO>> validateMessages = this.validador.validate(endereco, EnderecoInfo.class);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Endereco inválido", validateMessages);
		}

		ValidaCep validaCep = new ValidaCep();
		if (!validaCep.cepValido(endereco.getCep())) {
			throw new BadRequestException("O CEP informado não é válido");
		}

		Optional<ClientePessoaFisicaEntity> clientePf = Optional
				.ofNullable(this.clientePfServ.getClienteById(endereco.getClienteId()));

		if (clientePf.isEmpty()) {
			Optional<ClientePessoaJuridicaEntity> clientePj = Optional
					.ofNullable(this.clientePjServ.getClienteById(endereco.getClienteId()));
			if (clientePj.isEmpty()) {
				throw new BadRequestException("O cliente não foi encontrado");
			}
		}

		EnderecoEntity endEntity = new EnderecoFactory(endereco).toEntity();

		this.enderecoRepo.save(endEntity);

		return endereco;
	}
}
