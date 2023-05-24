package com.devsuperior.crud.controllers;

import com.devsuperior.crud.dto.ClientDTO;
import com.devsuperior.crud.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientController {

  @Autowired
  ClientService service;

  @GetMapping("/{id}")
  public ResponseEntity<ClientDTO> getClient(@PathVariable(value = "id") Long id) {
    ClientDTO productDTO = this.service.findById(id);

    return ResponseEntity.status(HttpStatus.OK).body(productDTO);
  }

  @GetMapping
  public ResponseEntity<Page<ClientDTO>> getClients(Pageable pageable) {
    Page<ClientDTO> result = this.service.findAll(pageable);

    return ResponseEntity.ok(result);
  }

  @PostMapping
  public ResponseEntity<ClientDTO> save(@RequestBody @Valid ClientDTO request) {
    ClientDTO result = this.service.save(request);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(result.getId()).toUri();

    return ResponseEntity.created(uri).body(result);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody @Valid ClientDTO request) {
    ClientDTO result = this.service.update(id, request);

    if (result == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().body(result);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
