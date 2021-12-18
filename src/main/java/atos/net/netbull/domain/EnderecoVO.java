package atos.net.netbull.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnderecoVO {
	
	@NotNull(message = "Campo id não pode ser nulo", groups = EnderecoInfo.class)
	private int id;

	@NotNull(message = "Campo logradouro não pode ser nulo", groups = EnderecoInfo.class)
	private String logradouro;
	

	@NotNull(message = "Campo numero não pode ser nulo", groups = EnderecoInfo.class)
	private String numero;	
	
	private String complemento;
	

	@NotNull(message = "Campo bairro não pode ser nulo", groups = EnderecoInfo.class)
	private String bairro;
	

	@NotNull(message = "Campo cidade não pode ser nulo", groups = EnderecoInfo.class)
	private String cidade;
	

	@NotNull(message = "Campo estado não pode ser nulo", groups = EnderecoInfo.class)
	private String estado;
	

	@NotNull(message = "Campo CEP não pode ser nulo", groups = EnderecoInfo.class)
	private String cep;
	
	@NotNull(message="Tipo não pode ser nulo")
	private TipoEnderecoEnum tipo;
	
	@NotNull(message = "Campo id do cliente não pode ser nulo", groups = EnderecoInfo.class)
	private Long clienteId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
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


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}

	public TipoEnderecoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnderecoEnum tipo) {
		this.tipo = tipo;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	} 
	
}