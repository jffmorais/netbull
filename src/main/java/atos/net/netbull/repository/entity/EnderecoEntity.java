package atos.net.netbull.repository.entity;

import javax.persistence.Column;
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
}
