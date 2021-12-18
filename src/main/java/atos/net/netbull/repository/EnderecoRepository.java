package atos.net.netbull.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import atos.net.netbull.repository.entity.EnderecoEntity;
import atos.net.netbull.repository.entity.EnderecoPK;

@Repository
public interface EnderecoRepository extends CrudRepository<EnderecoEntity, EnderecoPK>{
	
	@Modifying
	@Query(value = "DELETE FROM endereco e WHERE e.id_cliente=:clienteId AND e.nr_endereco=:endNum", nativeQuery = true)
	public void deletaEndereco(@Param("clienteId") Long clienteId, @Param("endNum") Integer endNum);
	
	@Modifying
	@Query(value = "SELECT (nr_endereco) FROM endereco e WHERE e.id_cliente=:clienteId", nativeQuery = true)
	public List<Integer> buscaQuantidadeEnderecosCliente(Long clienteId);

}
