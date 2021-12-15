package atos.net.netbull.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import atos.net.netbull.domain.EnderecoVO;
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
	
	public ResponseEntity<EnderecoVO> insert(@RequestBody EnderecoVO vo){
		vo = addService.persistir(vo);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(vo.getId()).toUri();
		
		return ResponseEntity.created(uri).body(vo);
	}
}
