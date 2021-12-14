package atos.net.netbull.repository.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import atos.net.netbull.domain.TipoEnderecoEnum;

@Entity
@Table(name = "ENDERECO")
public class EnderecoEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private EnderecoPK id;
	
	
	


	@Column(name = "LOGRADOURO")
	@NotNull(message = "Campo logradouro não pode ser nulo")
	private String logradouro;
	
	@Column(name = "NUMERO")
	@NotNull(message = "Campo numero não pode ser nulo")
	private int numero;
	
	@Column(name = "COMPLEMENTO")
	private String complemento;
	
	@Column(name = "BAIRRO")
	@NotNull(message = "Campo bairro não pode ser nulo")
	private String bairro;
	
	@Column(name = "CIDADE")
	@NotNull(message = "Campo cidade não pode ser nulo")
	private String cidade;
	
	@Column(name = "ESTADO")
	@NotNull(message = "Campo estado não pode ser nulo")
	private String estado;
	
	@Column(name = "CEP")
	@NotNull(message = "Campo cep não pode ser nulo")
	private String cep;
	
	@Column(name = "TIPO")
	@NotNull(message = "Campo tipo não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private TipoEnderecoEnum tipo;

	
	
	
	

	



	public EnderecoPK getId() {
		return id;
	}

	public void setId(EnderecoPK id) {
		this.id = id;
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
	
	
}
