package atos.net.netbull.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import atos.net.netbull.repository.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
