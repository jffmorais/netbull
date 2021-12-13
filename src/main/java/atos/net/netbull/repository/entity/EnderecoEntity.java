package atos.net.netbull.repository.entity;

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
public class EnderecoEntity {
	
	@Id 
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_CLIENTE")
	private ClienteEntity cliente;

	@Column(name = "LOGRADOURO")
	@NotNull(message = "Campo logradouro não pode ser nulo")
	private String logradouro;
	
	@Column(name = "NUMERO")
	@NotNull(message = "Campo numero não pode ser nulo")
	private String numero;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
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
	
	
}
