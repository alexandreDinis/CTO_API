package com.dinis.cto.repository;

import com.dinis.cto.model.person.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
