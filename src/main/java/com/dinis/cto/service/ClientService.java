package com.dinis.cto.service;


import com.dinis.cto.dto.person.ClientSummary;
import com.dinis.cto.dto.person.DataClientDTO;
import com.dinis.cto.model.person.Client;
import com.dinis.cto.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
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
        client.setStatus(true);
        repository.save(client);
    }

    public Page<ClientSummary> list(Pageable pageable) {
        return repository.findAll(pageable).map(client -> new ClientSummary(
                client.getId(),
                client.getFantasyName(),
                client.getAddress().getCity()
        ));
    }

    public DataClientDTO details(Long id) {

        var client = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Não há cliente com esse id "+ id+ " em nosso banco de dados." ));
        return new DataClientDTO(client);
    }
}
