package com.devsuperior.crudclient.services;

import com.devsuperior.crudclient.entities.Client;
import com.devsuperior.crudclient.repositories.ClientRepository;
import com.devsuperior.crudclient.services.exceptions.DatabaseException;
import com.devsuperior.crudclient.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client não encontrado"));
    }

    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Client insert(Client client) {
        return repository.save(client);
    }

    @Transactional
    public Client update(Long id, Client client) {
        try {
            Client entity = repository.getReferenceById(id);
            entity.setName(client.getName());
            entity.setCpf(client.getCpf());
            entity.setIncome(client.getIncome());
            entity.setBirthDate(client.getBirthDate());
            entity.setChildren(client.getChildren());
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Client não encontrado");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }
}
