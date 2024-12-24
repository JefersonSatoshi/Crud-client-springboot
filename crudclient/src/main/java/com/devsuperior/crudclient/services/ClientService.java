package com.devsuperior.crudclient.services;

import com.devsuperior.crudclient.entities.Client;
import com.devsuperior.crudclient.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        Client client = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Client não encontrado"));
        return client;
    }

    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Client insert(Client client) {
        Client entity = client;
        entity = repository.save(entity);
        return entity;
    }
}
