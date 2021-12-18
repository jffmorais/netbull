package atos.net.netbull.service;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import atos.net.netbull.domain.EnderecoInfo;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.factory.EnderecoFactory;
import atos.net.netbull.repository.ClienteRepository;
import atos.net.netbull.repository.EnderecoRepository;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.repository.entity.EnderecoEntity;

@Service
public class AlteraEnderecoService {

	private Validator validator;
	private ClienteRepository clienteRepo;
	private EnderecoRepository enderecoRepo;
	private boolean enderecoExiste = false;

	public AlteraEnderecoService(Validator validator, ClienteRepository clienteRepo, EnderecoRepository enderecoRepo) {
		super();
		this.validator = validator;
		this.clienteRepo = clienteRepo;
		this.enderecoRepo = enderecoRepo;
	}

	public EnderecoVO persistir(EnderecoVO vo) {
		Set<ConstraintViolation<EnderecoVO>> validateMessages = this.validator.validate(vo, EnderecoInfo.class);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Endereco inválido", validateMessages);
		}

		Optional<ClienteEntity> clienteExistente = this.clienteRepo.findById(vo.getClienteId());
		if (clienteExistente.isPresent()) {
			clienteExistente.get().getEnderecos().forEach(item -> {
				if (item.getId().getNumeroEndereco() == vo.getId()) {
					this.enderecoExiste = true;
				}
			});
			if (this.enderecoExiste) {
				EnderecoEntity endEntity = new EnderecoFactory(vo).toEntity();
				this.enderecoRepo.save(endEntity);
			} else {
				throw new NotFoundException("Endereço não encontrado");
			}
		} else {
			throw new NotFoundException("Cliente não encontrado");
		}

		return vo;
	}

}
