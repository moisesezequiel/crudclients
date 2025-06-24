package com.softwaresolutions.crudclients.services;

import com.softwaresolutions.crudclients.dto.ClientDTO;
import com.softwaresolutions.crudclients.entities.Client;
import com.softwaresolutions.crudclients.repositories.ClientRepository;
import com.softwaresolutions.crudclients.services.exceptions.ResourceNotFoundException;
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
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> clients = repository.findAll(pageable);
        return clients.map(x -> new ClientDTO(x));

    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client Client = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado"));
        return new ClientDTO(Client);

    }
    
    @Transactional()
    public ClientDTO insert(ClientDTO dto){
        Client entity = new Client();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new ClientDTO(entity);
    }

    @Transactional()
    public ClientDTO update(Long id, ClientDTO dto){

        try {

            Client entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);

            return new ClientDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não cadastrado em base de dados");
        }

    }

    private void copyDtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setBirthDate(dto.getBirthDate());
        entity.setIncome(dto.getIncome());
        entity.setChildren(dto.getChildren());

    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!repository.existsById(id)){
            throw  new ResourceNotFoundException("ID não encontrado");
        }else {
            repository.deleteById(id);

        }


    }
}
