package atos.net.netbull.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import atos.net.netbull.repository.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
	
///	public Optional<ClienteEntity> procuraClientePorId(Long id);
}
