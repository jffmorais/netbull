package atos.net.netbull.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atos.net.netbull.repository.entity.EnderecoEntity;

@Repository
public interface EnderecoRepository extends CrudRepository<EnderecoEntity, Long>{
	
//	@Modifying
//	@Query("delete from ENDERECO e where b.ID_CLIENTE=:clienteId and b.NR_ENDERECO=:endNum")
//	void deletaEndereco(@Param("clienteId") Long id, @Param("endNum") Integer endNum);

}
