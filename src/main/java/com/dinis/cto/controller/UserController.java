package com.dinis.cto.controller;

import com.dinis.cto.dto.person.*;
import com.dinis.cto.infra.security.SecurityUtils;
import com.dinis.cto.infra.security.TokenJWT;
import com.dinis.cto.infra.security.TokenService;
import com.dinis.cto.model.person.User;
import com.dinis.cto.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PutMapping("/update")
    public ResponseEntity<DataUserDTO> updateUser(HttpServletRequest request, @RequestBody DataUserUpdateDTO data) {
        // Obter o usuário autenticado para pegar o ID
        User authenticatedUser = SecurityUtils.authenticateAndGetUser(request);

        // Chamar o método de atualização passando o ID do usuário
        DataUserDTO updatedUser = service.updateUser(request, authenticatedUser.getId(), data);

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenJWT> login (@RequestBody @Valid AuthenticationDTO data) {
        var authentication = service.authentication(data);
        var token = tokenService.gerarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJWT(token));
    }
    //todo:testar Nesse passo eu pretendo implementar no front um botao alterar
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(HttpServletRequest request, @RequestBody DataPasswordUpdateDTO data) {
        try {
            service.updatePassword(request, data);
            return ResponseEntity.ok("Senha alterada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    // Endpoint para enviar o e-mail de confirmação de cadastro
//    @PostMapping("/send-confirmation-email")
//    public ResponseEntity<Void> sendConfirmationEmail(@RequestBody @Valid EmailDTO data) {
//        try {
//            service.sendConfirmationEmail(data.email());
//            return ResponseEntity.ok().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build(); // Retorna 400 se o e-mail já estiver cadastrado
//        }
//    }
}
