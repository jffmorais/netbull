package atos.net.netbull.repository.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import atos.net.netbull.domain.TipoClienteEnum;

@Entity
@DiscriminatorValue("PF")
public class ClientePessoaFisicaEntity extends ClienteEntity{

	public ClientePessoaFisicaEntity() {
		super.setTipo(TipoClienteEnum.PF);
	}
	
	@Column(name = "NOME")
	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;
	
	@Column(name = "CPF")
	@NotNull(message = "Campo cpf não pode ser nulo")
	private String cpf;
	
	@Column(name = "DT_NASCIMENTO")
	@NotNull(message = "Campo data de nascimento não pode ser nulo")
	private LocalDate dtNascimento;

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

}
