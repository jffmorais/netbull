package atos.net.netbull.domain;

import javax.validation.constraints.NotNull;

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

	public void setCEP(String cEP) {
		CEP = cEP;
	}
	
	
}
