package com.dinis.cto.service;


import com.dinis.cto.dto.os.*;
import com.dinis.cto.model.car.ClientCar;
import com.dinis.cto.model.os.BudgetEnum;
import com.dinis.cto.model.os.OrderWork;
import com.dinis.cto.model.os.Parts;
import com.dinis.cto.model.os.Work;
import com.dinis.cto.repository.ClientCarRepository;
import com.dinis.cto.repository.ClientRepository;
import com.dinis.cto.repository.OrderWorkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderWorkService {


    @Autowired
    private OrderWorkRepository orderWorkRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientCarRepository clientCarRepository;


    public void openOS(DataOrderWorkDTO data) {
        var clientOptional = clientRepository.findById(data.clientId());

        if (clientOptional.isPresent()) {
            var client = clientOptional.get();

            var orderWork = new OrderWork(data);
            orderWork.setClient(client);
            orderWork.setBudget(BudgetEnum.ORÇAMENTO);

            List<Work> works = data.works().stream().map(workDTO -> {
                ClientCar car = new ClientCar(workDTO.car());
                car.setCreateDate(workDTO.car().createDate());
                car.setClient(client);
                clientCarRepository.save(car);

                Work work = new Work(workDTO);
                work.setCar(car);
                work.setOrderWork(orderWork);


                List<Parts> parts = workDTO.parts().stream().map(partDTO -> {
                    Parts part = new Parts(partDTO);
                    part.setWork(work);
                    return part;
                }).collect(Collectors.toList());
                work.setParts(parts);

                return work;
            }).collect(Collectors.toList());

            orderWork.setWorks(works);

            calculateAndSetWorkValues(orderWork);
            calculateAndSetServiceValue(orderWork);
            orderWork.setStatus(true);

            orderWorkRepository.save(orderWork);
        } else {
            throw new EntityNotFoundException("Client not found with id: " + data.clientId());
        }
    }

    private void calculateAndSetWorkValues(OrderWork orderWork) {

    }
    private void calculateAndSetServiceValue(OrderWork orderWork) {
        BigDecimal totalServiceValue = orderWork.getWorks().stream()
                .map(Work::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderWork.setServiceValue(totalServiceValue);
    }

    public PaginatedResponseWithTotal<ResponseOsTrueDTO> listStatusTrue(Pageable pageable) {
        Page<OrderWork> orderWorks = orderWorkRepository.findByStatusTrue(pageable);
        Page<ResponseOsTrueDTO> responseOsTrueDTOPage = orderWorks.map(ResponseOsTrueDTO::new);

        BigDecimal totalValue = calculateTotalServiceValue(orderWorks.getContent());


        return new PaginatedResponseWithTotal<>(responseOsTrueDTOPage, totalValue);
    }

    public PaginatedResponseWithTotal<ResponseOsFalseDTO> listStatusFalse(Pageable pageable) {
        Page<OrderWork> orderWorks = orderWorkRepository.findByStatusFalse(pageable);
        Page<ResponseOsFalseDTO> responseOsFalseDTOPage = orderWorks.map(ResponseOsFalseDTO::new);

        BigDecimal totalValue = calculateTotalServiceValue(orderWorks.getContent());

        return new PaginatedResponseWithTotal<>(responseOsFalseDTOPage, totalValue);
    }

    public DetailOsDTO getOrderWorkDetails(Long id) {
        var orderWork = orderWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderWork not found with id: " + id));

        orderWork.getWorks().forEach(work -> {
            System.out.println("Work ID: " + work.getId());
            work.getParts().forEach(part -> System.out.println("Part ID: " + part.getId()));
        });

            return new DetailOsDTO(orderWork);

    }
    public void applyDiscount(Long id, DataOsDiscountDTO data) {
        OrderWork orderWork = orderWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderWork not found with id: " + id));

        BigDecimal serviceValue = orderWork.getServiceValue();
        if (data.value() != null && data.percent() == null) {
            BigDecimal discountValue = data.value();
            orderWork.setDiscountValue(discountValue);
            BigDecimal valueTotal = serviceValue.subtract(discountValue);
            orderWork.setValueTotal(valueTotal);
        } else if (data.value() == null && data.percent() != null) {
            BigDecimal discountPercentage = data.percent();
            orderWork.setDiscountPercentage(discountPercentage);
            BigDecimal discountAmount = serviceValue.multiply(discountPercentage).divide(BigDecimal.valueOf(100));
            BigDecimal valueTotal = serviceValue.subtract(discountAmount);
            orderWork.setValueTotal(valueTotal);
        } else {
            throw new IllegalArgumentException("Either value or percent must be provided, not both.");
        }

        orderWorkRepository.save(orderWork);
    }

    public void closeOs(Long id) {
        OrderWork orderWork = orderWorkRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("OrderWork not found with id: " + id));
        var discountPercentage = orderWork.getDiscountPercentage();
        var discountValue = orderWork.getDiscountValue();
        if(discountValue == null && discountPercentage == null) {
            var serviceValue = orderWork.getServiceValue();
            orderWork.setValueTotal(serviceValue);
            orderWork.closeOs();
        } else {

            orderWork.closeOs();
        }

    }

    public BigDecimal calculateTotalServiceValue(List<OrderWork> orderWorks) {
        return orderWorks.stream()
                .map(OrderWork::getServiceValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<DetailOsDTO> findOrderWorksByClientAndCarPlate(ClientCarSearchDTO searchDTO) {
        List<OrderWork> orderWorks = orderWorkRepository.findByClientIdAndCarPlate(searchDTO.clientId(), searchDTO.plate());

        if (orderWorks.isEmpty()) {
            throw new EntityNotFoundException("Esse carro não consta no banco de dados");
        }

        return orderWorks.stream()
                .map(DetailOsDTO::new)
                .collect(Collectors.toList());
    }
}

