package atos.net.netbull.repository.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import atos.net.netbull.domain.PessoaFisicaInfo;
import atos.net.netbull.domain.PessoaJuridicaInfo;
import atos.net.netbull.domain.TipoClienteEnum;

@Entity
@Table(name = "CLIENTE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_CLIENTE", discriminatorType = DiscriminatorType.STRING)
public class ClienteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410877415473388527L;

	@Id
	@Column(name = "ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sq_cli", sequenceName = "sequence_cli", allocationSize = 1, initialValue = 1)
	private Long id;

	@Column(name = "TIPO_CLIENTE", insertable = false, updatable = false)
	@NotNull(message = "Campo tipo de cliente não pode ser nulo")
	@Enumerated(EnumType.STRING)
	private TipoClienteEnum tipo;

	@Column(name = "DT_CRIACAO")
	private LocalDateTime dtCriacao;

	@Column(name = "EMAIL")
	@NotNull(message = "Campo email não pode ser nulo")
	@Email(message = "Campo Email precisa ser válido")
	private String email;

	@Column(name = "TELEFONE")
	@NotNull(message = "Campo telefone não pode ser nulo")
	private String telefone;

	@Column(name = "NOME")
	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;

	@Column(name = "CPF")
	@NotNull(message = "Campo cpf não pode ser nulo")
	private String cpf;

	@Column(name = "DT_NASCIMENTO")
	@NotNull(message = "Campo data de nascimento não pode ser nulo")
	private LocalDate dtNascimento;

	@Column(name = "RAZAO_SOCIAL")
	@NotNull(message = "Campo razão social não pode ser nulo")
	private String razaoSocial;

	@Column(name = "CNPJ")
	@NotNull(message = "Campo cnpj não pode ser nulo")
	private String cnpj;

	@OneToMany(mappedBy = "id.cliente", cascade = CascadeType.ALL)
	@NotNull(message = "Deve-se adicionar ao menos um e no máximo três endereços")
	@Size(min = 1, max = 3, message = "Deve-se adicionar ao menos um e no máximo três endereços")
	private List<EnderecoEntity> enderecos;

	public LocalDateTime getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(LocalDateTime dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoClienteEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoClienteEnum tipo) {
		this.tipo = tipo;
	}

	public List<EnderecoEntity> getEnderecos() {
		return enderecos;
	}

	public void setEndereco(List<EnderecoEntity> endereco) {
		this.enderecos = endereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteEntity other = (ClienteEntity) obj;
		return Objects.equals(id, other.id);
	}

	public void add(EnderecoEntity endereco) {
		List<EnderecoEntity> enderecosLocal = Optional.ofNullable(this.getEnderecos()).orElseGet(() -> new ArrayList());
		enderecosLocal.add(endereco);

		this.enderecos = enderecosLocal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setEnderecos(List<EnderecoEntity> enderecos) {
		this.enderecos = enderecos;
	}

}
