package com.dinis.cto.repository;

import com.dinis.cto.model.os.OrderWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderWorkRepository extends JpaRepository<OrderWork,Long> {

    Page<OrderWork> findByStatusTrue(Pageable pageable);

    Page<OrderWork> findByStatusFalse(Pageable pageable);
}
