package com.devsuperior.crud.services;

import com.devsuperior.crud.dto.ClientDTO;
import com.devsuperior.crud.model.Client;
import com.devsuperior.crud.respositories.ClientRepository;
import com.devsuperior.crud.services.exceptions.DataIntegrityViolationCustomException;
import com.devsuperior.crud.services.exceptions.ResourceFoundException;
import com.devsuperior.crud.services.exceptions.ResourceNotFoundException;
import com.devsuperior.crud.utils.ModelMapperUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClientService {

  @Autowired
  private ClientRepository repository;

  public ClientDTO findById(Long id) {
    Client Client = this.repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Cliente inexistente"));

    return ModelMapperUtils.entityToDto(Client, new ClientDTO().getClass());
  }

  @Transactional(readOnly = true)
  public Page<ClientDTO> findAll(Pageable pageable) {
    Page<Client> result = this.repository.findAll(pageable);
    return result.map(x -> ModelMapperUtils.entityToDto(x, ClientDTO.class));
  }

  public ClientDTO save(ClientDTO request) {
    Optional<Client> existsClient = this.repository.findByName(request.getName());

    if (existsClient.isPresent()) {
      throw new ResourceFoundException("Cliente já cadastrado");
    }
    Client result = this.repository.save(ModelMapperUtils.dtoToEntity(request, Client.class));

    return ModelMapperUtils.entityToDto(result, ClientDTO.class);
  }

  public ClientDTO update(Long id, ClientDTO requestDTO) {

    Client entity = this.repository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Cliente inexistente"));

    BeanUtils.copyProperties(requestDTO, entity, "id");

    Client result = this.repository.save(entity);
    return ModelMapperUtils.entityToDto(result, ClientDTO.class);
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    try {
      this.repository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Cliente inexistente");
    } catch (DataIntegrityViolationException e) {
      throw new DataIntegrityViolationCustomException("Falha de integridade referencial");
    }

  }


}
