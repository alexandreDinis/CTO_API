package com.dinis.cto.service;


import com.dinis.cto.dto.person.DataClientDTO;
import com.dinis.cto.model.person.Client;
import com.dinis.cto.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;


    public void register(DataClientDTO data) {

        var client = new Client(data);
        repository.save(client);
    }

    public Page<DataClientDTO> list(Pageable pageable) {
        return repository.findAll(pageable).map(DataClientDTO::new);
    }
}
