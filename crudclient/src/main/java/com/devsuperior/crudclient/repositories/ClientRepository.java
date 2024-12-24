package com.devsuperior.crudclient.repositories;

import com.devsuperior.crudclient.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
