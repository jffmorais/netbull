package atos.net.netbull.repository.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CLIENTE")
public class ClienteEntity {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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
	
	public ClienteEntity(String nome, String cpf, LocalDate dtNascimento, LocalDateTime dtCriacao, String email,
			String telefone) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dtNascimento = dtNascimento;
		this.dtCriacao = dtCriacao;
		this.email = email;
		this.telefone = telefone;
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
