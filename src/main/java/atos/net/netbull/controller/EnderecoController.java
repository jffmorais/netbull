package atos.net.netbull.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import atos.net.netbull.domain.EnderecoVO;
import atos.net.netbull.factory.EnderecoFactory;
import atos.net.netbull.repository.entity.ClienteEntity;
import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.repository.entity.EnderecoPK;
import atos.net.netbull.service.AdicionaEnderecoService;
import atos.net.netbull.service.AlteraEnderecoService;
import atos.net.netbull.service.DeletaEnderecoService;

@RestController
@RequestMapping(value="/v1/endereco")
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
	
	@PostMapping
	public ResponseEntity<EnderecoVO> insert(@RequestBody EnderecoVO vo){
		vo = addService.persistir(vo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(vo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(vo);
	}
	
	@PatchMapping(value="/editar/{id}/{endNum}")
	public ResponseEntity<EnderecoVO> altera(@RequestBody EnderecoVO vo, @PathVariable Long id, @PathVariable Integer endNum){
		
		EnderecoEntity endEntity = new EnderecoFactory(vo).toEntity();
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setId(id);
		
		EnderecoPK endPK = new EnderecoPK();
		endPK.setCliente(clienteEntity);
		endPK.setNumeroEndereco(endNum);
		
		endEntity.setId(endPK);
		
		clienteEntity.add(endEntity);
		
		this.alteraService.persistir(clienteEntity);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping(value = "/remover/{id}/{endNum}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @PathVariable Integer endNum){
		deletaService.excluiEndereco(id, endNum);
		return ResponseEntity.noContent().build();		
	}
}
