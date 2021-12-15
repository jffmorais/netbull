package atos.net.netbull.controller;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import atos.net.netbull.domain.ClienteVO;
import atos.net.netbull.service.BuscaClienteService;
import atos.net.netbull.service.CriaClienteService;
import atos.net.netbull.service.DeletaClienteService;

@RestController
@RequestMapping(value="/v1/clientes")
//@Tag(name = "Clientes")
public class ClienteController {
	
	private CriaClienteService service;
	private BuscaClienteService buscaClienteService;
	private DeletaClienteService deletaClienteService;
	
	public ClienteController(CriaClienteService service, BuscaClienteService buscaClienteService,DeletaClienteService deletaClienteService) {
		super();
		this.service = service;
		this.buscaClienteService = buscaClienteService;
		this.deletaClienteService = deletaClienteService;
		
		
	}
	
	@PostMapping
	public ResponseEntity<ClienteVO> insert( @RequestBody  ClienteVO vo){
		
		vo = service.persistir(vo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(vo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(vo);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public ResponseEntity<ClienteVO> findById(@PathVariable("id") Long id){
		ClienteVO clienteEncontrado = buscaClienteService.porId(id);
		
		return ResponseEntity.ok(clienteEncontrado);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		deletaClienteService.deletaCliente(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<Page<ClienteVO>> buscaTodos(Pageable pageable){
		
		Page<ClienteVO> lista = buscaClienteService.findAllPaged(pageable);
		
		return ResponseEntity.ok().body(lista);
	}
}