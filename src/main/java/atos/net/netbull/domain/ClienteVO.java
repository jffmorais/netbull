package atos.net.netbull.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClienteVO {



	private Long id;

	@NotNull(message = "Campo nome não pode ser nulo")
	private String nome;

	@NotNull(message = "Campo cpf não pode ser nulo")
	private String cpf;
	
	@NotNull(message = "Campo data de nascimento não pode ser nulo")
	//@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dtNascimento;
	
	private LocalDateTime dtCriacao;
	
	@NotNull(message = "Campo email não pode ser nulo")
	private String email;
	
	@NotNull(message = "Campo telefone não pode ser nulo")
	private String telefone;
	
	@NotNull(message = "Campo Itens deve ter pelo menos um item")
	@Size(min = 1, message = "Campo Endereco deve ter pelo menos um item")
	@Valid
	private List<EnderecoVO> Enderecos;
	

	

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<EnderecoVO> getEnderecos() {
		return Enderecos;
	}

	public void setEnderecos(List<EnderecoVO> enderecos) {
		Enderecos = enderecos;
	}

	public void add(EnderecoVO endereco) {
		List<EnderecoVO> enderecosLocal = 
				Optional.ofNullable(this.getEnderecos()).orElseGet(()->new ArrayList());		
		enderecosLocal.add(endereco);
		
		this.Enderecos = enderecosLocal;
		
	}
	
	
}