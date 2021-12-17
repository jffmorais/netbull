package atos.net.netbull.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteVO {

	private Long id;

	@NotNull(message = "Campo tipo de cliente não pode ser nulo", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	private TipoClienteEnum tipo;
	
	@NotNull(message = "Campo nome não pode ser nulo", groups = PessoaFisicaInfo.class)
	private String nome;
	
	@NotNull(message = "Campo razão social não pode ser nulo", groups = PessoaJuridicaInfo.class)
	private String razaoSocial;

	@NotNull(message = "Campo cpf não pode ser nulo", groups = PessoaFisicaInfo.class)
	private String cpf;
	
	@NotNull(message = "Campo cnpj não pode ser nulo", groups = PessoaJuridicaInfo.class)
	private String cnpj;
	
	@NotNull(message = "Campo data de nascimento não pode ser nulo", groups = PessoaFisicaInfo.class)
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate dtNascimento;
	
	private LocalDateTime dtCriacao;
	
	@NotNull(message = "Campo email não pode ser nulo", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	@Email(message = "Campo Email precisa ser válido", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	private String email;
	
	@NotNull(message = "Campo telefone não pode ser nulo", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	private String telefone;
	
	@NotNull(message = "Campo Endereço não pode ser nulo", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	@Size(min = 1, max = 3, message = "Deve-se adicionar ao menos um e no máximo três endereços", groups = {PessoaFisicaInfo.class, PessoaJuridicaInfo.class})
	@Valid
	private List<EnderecoVO> enderecos;

	

	
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

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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

	public TipoClienteEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoClienteEnum tipo) {
		this.tipo = tipo;
	}

	public List<EnderecoVO> getEnderecos() {
		return enderecos;
	}

	public void addEndereco(EnderecoVO endereco) {
		List<EnderecoVO> enderecosList =
				Optional.ofNullable(this.getEnderecos()).orElseGet(()->new ArrayList());
		enderecosList.add(endereco);
		this.enderecos = enderecosList;
	}
}