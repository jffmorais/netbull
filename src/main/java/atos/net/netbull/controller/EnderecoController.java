package atos.net.netbull.controller;

import java.net.URI;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.service.AdicionaEnderecoService;
import atos.net.netbull.service.AlteraEnderecoService;
import atos.net.netbull.service.DeletaEnderecoService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/v1/endereco")
@Tag(name = "Endereço")
public class EnderecoController {

	private AdicionaEnderecoService addService;
	private AlteraEnderecoService alteraService;
	private DeletaEnderecoService deletaService;

	public EnderecoController(AdicionaEnderecoService addService, AlteraEnderecoService alteraService,
			DeletaEnderecoService deletaService) {
		super();
		this.addService = addService;
		this.alteraService = alteraService;
		this.deletaService = deletaService;
	}

	@PostMapping(value = "/adicionar/{clienteId}")
	public ResponseEntity<EnderecoVO> insert(@RequestBody EnderecoVO vo, @PathVariable Long clienteId) {

		vo.setClienteId(clienteId);
		try {
			vo = addService.persistir(vo);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.badRequest().build();
		}

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vo.getId()).toUri();

		return ResponseEntity.created(uri).body(vo);
	}

	@PutMapping(value = "/editar/{clienteId}/{enderecoId}")
	public ResponseEntity<EnderecoVO> altera(@RequestBody EnderecoVO vo, @PathVariable Long clienteId,
			@PathVariable Integer enderecoId) {

		vo.setId(enderecoId);
		vo.setClienteId(clienteId);

		EnderecoVO endAlterado = null;
		
		try {
			endAlterado = this.alteraService.persistir(vo);
		}catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(endAlterado);
	}

	@DeleteMapping(value = "/remover/{clienteId}/{enderecoId}")
	public ResponseEntity<Void> delete(@PathVariable Long clienteId, @PathVariable Integer enderecoId) {
		try {
			deletaService.excluiEndereco(clienteId, enderecoId);
		}catch (BadRequestException e) {
			return ResponseEntity.badRequest().build();
		}catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.noContent().build();
	}
}
