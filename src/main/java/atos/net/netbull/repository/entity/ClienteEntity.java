package atos.net.netbull.repository.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CLIENTE")
public class ClienteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7410877415473388527L;

	@Id
	@Column(name = "ID_CLIENTE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NOME")
	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;
	
	@Column(name = "CPF")
	@NotNull(message = "Campo cpf não pode ser nulo")
	private String cpf;
	
	@Column(name = "DT_NASCIMENTO")
	@NotNull(message = "Campo data de nascimento não pode ser nulo")
	private LocalDate dtNascimento;
	
	@Column(name = "DT_CRIACAO")
	private LocalDateTime dtCriacao;
	
	@Column(name = "EMAIL")
	@NotNull(message = "Campo email não pode ser nulo")
	private String email;
	
	@Column(name = "TELEFONE")
	@NotNull(message = "Campo telefone não pode ser nulo")
	private String telefone;
	
	@OneToMany(mappedBy = "endereco")
	@NotNull(message = "Deve-se adicionar ao menos um e no máximo três endereços")
	@Size(min = 1, max = 3, message = "Deve-se adicionar ao menos um e no máximo três endereços")
	private List<EnderecoEntity> endereco;
	
	
	
	public ClienteEntity(Long id, @NotNull(message = "Campo nome não pode ser nulo") String nome,
			@NotNull(message = "Campo cpf não pode ser nulo") String cpf,
			@NotNull(message = "Campo data de nascimento não pode ser nulo") LocalDate dtNascimento,
			LocalDateTime dtCriacao, @NotNull(message = "Campo email não pode ser nulo") String email,
			@NotNull(message = "Campo telefone não pode ser nulo") String telefone,
			@NotNull(message = "Deve-se adicionar ao menos um e no máximo três endereços") @Size(min = 1, max = 3, message = "Deve-se adicionar ao menos um e no máximo três endereços") List<EnderecoEntity> endereco) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dtNascimento = dtNascimento;
		this.dtCriacao = dtCriacao;
		this.email = email;
		this.telefone = telefone;
		this.endereco = endereco;
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
	
}
