package atos.net.netbull.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.domain.EnderecoInfo;
import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.factory.EnderecoFactory;
import atos.net.netbull.repository.EnderecoRepository;
import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.util.ValidaCep;

@Service
public class AdicionaEnderecoService {

	private Validator validador;
	private EnderecoRepository enderecoRepo;
	private BuscaClienteService buscaCliente;

	public AdicionaEnderecoService(Validator validador, EnderecoRepository enderecoRepo,
			BuscaClienteService buscaCliente) {
		super();
		this.validador = validador;
		this.enderecoRepo = enderecoRepo;
		this.buscaCliente = buscaCliente;
	}

	@Transactional
	public EnderecoVO persistir(@NotNull EnderecoVO endereco) {
		Set<ConstraintViolation<EnderecoVO>> validateMessages = this.validador.validate(endereco, EnderecoInfo.class);

		if (!validateMessages.isEmpty()) {
			throw new ConstraintViolationException("Endereço inválido", validateMessages);
		}

		ValidaCep validaCep = new ValidaCep();
		if (!validaCep.cepValido(endereco.getCep())) {
			throw new BadRequestException("O CEP informado não é válido");
		}

		Optional<ClienteVO> cliente = Optional.ofNullable(this.buscaCliente.porId(endereco.getClienteId()));
		
		if(cliente.isEmpty()) {
			throw new BadRequestException("O cliente não foi encontrado");
		}
		
		ArrayList<Integer> possibilidadesId = new ArrayList<Integer>(Arrays.asList(1,2,3));  
		List<Integer> nrEnderecos = new ArrayList<Integer>();
		
		cliente.get().getEnderecos().forEach(end -> {
			nrEnderecos.add(end.getId());
		});
		
		if(nrEnderecos.size() >= 3) {
			throw new BadRequestException("A quantidade máxima de endereços foi atingida");
		}
		
		possibilidadesId.removeAll(nrEnderecos);
		
		endereco.setId(possibilidadesId.get(0));

		EnderecoEntity endEntity = new EnderecoFactory(endereco).toEntity();

		this.enderecoRepo.save(endEntity);

		return endereco;
	}
}
