package com.dinis.cto.service;


import com.dinis.cto.dto.os.DataOrderWorkDTO;
import com.dinis.cto.repository.OrderWorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderWorkService {


    @Autowired
    private OrderWorkRepository orderWorkRepository;


    public void register(DataOrderWorkDTO data) {
    }
}
