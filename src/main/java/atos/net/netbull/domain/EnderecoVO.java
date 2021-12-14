package atos.net.netbull.domain;

import javax.validation.constraints.NotNull;

import atos.net.netbull.repository.entity.EnderecoEntity;

public class EnderecoVO {
	
	
	private Long id;
	
	@NotNull(message = "Campo logradouro não pode ser nulo")
	private String logradouro;
	
	@NotNull(message = "Campo numero não pode ser nulo")
	private int numero;	
	
	private String complemento;
	
	@NotNull(message = "Campo bairro não pode ser nulo")
	private String bairro;
	
	@NotNull(message = "Campo cidade não pode ser nulo")
	private String cidade;
	
	@NotNull(message = "Campo estado não pode ser nulo")
	private String estado;
	
	@NotNull(message = "Campo CEP não pode ser nulo")
	private String CEP;
	
	@NotNull(message="Tipo não pode ser nulo")
	private TipoEnderecoEnum tipo; 


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoEnderecoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnderecoEnum tipo) {
		this.tipo = tipo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCEP() {
		return CEP;
	}

	public void setCEP(String CEP) {
		CEP = CEP;
	}
	
	
}








//public EnderecoVO(Long id, @NotNull(message = "Campo logradouro não pode ser nulo") String logradouro,
//@NotNull(message = "Campo numero não pode ser nulo") int numero, String complemento,
//@NotNull(message = "Campo bairro não pode ser nulo") String bairro,
//@NotNull(message = "Campo cidade não pode ser nulo") String cidade,
//@NotNull(message = "Campo estado não pode ser nulo") String estado,
//@NotNull(message = "Campo CEP não pode ser nulo") String cEP,
//@NotNull(message = "Tipo não pode ser nulo") TipoEnderecoEnum tipo) {
//super();
//this.id = id;
//this.logradouro = logradouro;
//this.numero = numero;
//this.complemento = complemento;
//this.bairro = bairro;
//this.cidade = cidade;
//this.estado = estado;
//CEP = cEP;
//this.tipo = tipo;
//}
//
//public EnderecoVO(EnderecoEntity entity) {
//
//this.id = entity.getId();
//this.logradouro = entity.getLogradouro();
//this.numero = entity.getNumero();
//this.complemento = entity.getComplemento();
//this.bairro = entity.getBairro();
//this.cidade = entity.getCidade();
//this.estado = entity.getEstado();
//this.CEP = entity.getCep();
//this.tipo = entity.getTipo();
//
//}
