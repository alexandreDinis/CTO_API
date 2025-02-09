package com.dinis.cto.controller;

import com.dinis.cto.dto.person.AuthenticationDTO;
import com.dinis.cto.dto.person.DataPasswordUpdateDTO;
import com.dinis.cto.dto.person.DataUserDTO;
import com.dinis.cto.dto.person.DataUserUpdateDTO;
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
import org.springframework.web.bind.annotation.*;

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

    //todo:testar emplementada em 09-02-2025
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody DataUserUpdateDTO data) {
        User updatedUser = service.updateUser(id, data);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJWT> login (@RequestBody @Valid AuthenticationDTO data) {
        var authentication = service.authentication(data);
        var token = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWT(token));
    }
    //todo:testar Nesse passo eu pretendo implementar no front um botao alterar
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody DataPasswordUpdateDTO data) {
        try {
            service.updatePassword(id, data);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
