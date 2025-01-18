package com.dinis.cto.controller;

import com.dinis.cto.dto.person.AuthenticationDTO;
import com.dinis.cto.dto.person.DataUserDTO;
import com.dinis.cto.infra.security.TokenJWT;
import com.dinis.cto.infra.security.TokenService;
import com.dinis.cto.model.person.User;
import com.dinis.cto.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;


    @PostMapping("/register")
    @Transactional
    public ResponseEntity<DataUserDTO> register(@RequestBody @Valid DataUserDTO data){
        service.regiter(data);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJWT> login (@RequestBody @Valid AuthenticationDTO data) {
        var authentication = service.authentication(data);
        var token = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWT(token));
    }
}
