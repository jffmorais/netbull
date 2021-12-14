package atos.net.netbull.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import atos.net.netbull.repository.entity.ClientePessoaFisicaEntity;

@Repository
public interface ClientePessoaFisicaRepository extends CrudRepository<ClientePessoaFisicaEntity, Long>{

}
