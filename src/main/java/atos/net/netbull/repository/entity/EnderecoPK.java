package atos.net.netbull.repository.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

public class EnderecoPK implements Serializable{
	
/**
 * SERIAL UID
 */
	private static final long serialVersionUID = 5240150106898767721L;
	
	@Column(name = "NR_ENDERECO")
	@NotNull(message = "Campo numero do endereco não pode ser nulo")
	private Integer numeroEndereco;
	
	@ManyToOne
	@JoinColumn(name="ID_CLIENTE")
	private ClienteEntity cliente;

	public Integer getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(Integer numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cliente, numeroEndereco);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoPK other = (EnderecoPK) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(numeroEndereco, other.numeroEndereco);
	}
		

}
