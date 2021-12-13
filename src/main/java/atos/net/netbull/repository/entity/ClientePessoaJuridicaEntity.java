package atos.net.netbull.repository.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import atos.net.netbull.domain.TipoClienteEnum;

@Entity
@DiscriminatorValue("PJ")
public class ClientePessoaJuridicaEntity extends ClienteEntity {
	
	public ClientePessoaJuridicaEntity() {
		super.setTipo(TipoClienteEnum.PJ);
	}	
	
	@Column(name = "RAZAO_SOCIAL")
	@NotNull(message = "Campo razão social não pode ser nulo")
	private String razaoSocial;
	
	@Column(name = "CNPJ")
	@NotNull(message = "Campo cnpj não pode ser nulo")
	private String cnpj;

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

}
