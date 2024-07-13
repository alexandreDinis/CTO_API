package com.dinis.cto.repository;

import com.dinis.cto.model.person.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
