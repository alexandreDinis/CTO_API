package com.dinis.cto.repository;

import com.dinis.cto.model.os.Parts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartsRepository extends JpaRepository<Parts, Long> {

}
