package com.softwaresolutions.crudclients.services;

import com.softwaresolutions.crudclients.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;
}
