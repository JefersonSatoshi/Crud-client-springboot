package com.devsuperior.crudclient.controllers;

import com.devsuperior.crudclient.entities.Client;
import com.devsuperior.crudclient.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id) {
        Client client = service.findById(id);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<Page<Client>> findAll(Pageable pageable) {
        Page<Client> clients = service.findAll(pageable);
        return ResponseEntity.ok(clients);
    }

    @PostMapping
    public ResponseEntity<Client> insert(@Valid @RequestBody Client client) {
        client = service.insert(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).body(client);
    }
}
