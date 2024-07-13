package com.dinis.cto.repository;

import com.dinis.cto.model.person.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
}
