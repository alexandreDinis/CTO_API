package com.dinis.cto.service;


import com.dinis.cto.dto.os.DataOrderWorkDTO;
import com.dinis.cto.dto.person.DetailOsDTO;
import com.dinis.cto.dto.person.ResponseOsFalseDTO;
import com.dinis.cto.dto.person.ResponseOsTrueDTO;
import com.dinis.cto.model.os.OrderWork;
import com.dinis.cto.model.os.Parts;
import com.dinis.cto.model.os.Work;
import com.dinis.cto.repository.ClientRepository;
import com.dinis.cto.repository.OrderWorkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderWorkService {


    @Autowired
    private OrderWorkRepository orderWorkRepository;

    @Autowired
    private ClientRepository clientRepository;


    public void openOS(DataOrderWorkDTO data) {

        var clientOptional = clientRepository.findById(data.clientId());

        if (clientOptional.isPresent()) {
            var client = clientOptional.get();

            var orderWork = new OrderWork(data);

            orderWork.setClient(client);

            calculateAndSetWorkValues(orderWork);
            calculateAndSetServiceValue(orderWork);
            orderWork.setStatus(true);

            orderWorkRepository.save(orderWork);
        } else {

            throw new EntityNotFoundException("Client not found with id: " + data.clientId());
        }
    }

    private void calculateAndSetWorkValues(OrderWork orderWork) {
        for (Work work : orderWork.getWorks()) {
            BigDecimal totalValue = work.getParts().stream()
                    .map(Parts::getValue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            work.setValue(totalValue);
        }
    }
    private void calculateAndSetServiceValue(OrderWork orderWork) {
        BigDecimal totalServiceValue = orderWork.getWorks().stream()
                .map(Work::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderWork.setServiceValue(totalServiceValue);
    }

    public Page<ResponseOsTrueDTO> listStatusTrue(Pageable pageable) {
        Page<OrderWork> orderWorks = orderWorkRepository.findByStatusTrue(pageable);
        return orderWorks.map(ResponseOsTrueDTO::new);
    }

    public Page<ResponseOsFalseDTO> listStatusFalse(Pageable pageable) {
        Page<OrderWork> orderWorks = orderWorkRepository.findByStatusFalse(pageable);
        return orderWorks.map(ResponseOsFalseDTO::new);

    }

    public DetailOsDTO getOrderWorkDetails(Long id) {
        var orderWork = orderWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderWork not found with id: " + id));


            return new DetailOsDTO(orderWork);

    }
}
