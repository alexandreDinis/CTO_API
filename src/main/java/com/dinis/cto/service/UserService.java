package com.dinis.cto.service;

import com.dinis.cto.dto.person.*;
import com.dinis.cto.dto.report.FirstNameDTO;
import com.dinis.cto.infra.security.TokenService;
import com.dinis.cto.model.person.Address;
import com.dinis.cto.model.person.Contact;
import com.dinis.cto.model.person.Phone;
import com.dinis.cto.model.person.User;
import com.dinis.cto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TokenService tokenService;


    public void regiter(DataUserDTO data) {

        UserDetails existingUser = repository.findByContactEmail(data.contact().email());

        if(existingUser != null) {
            throw new IllegalArgumentException("E-mail já cadastrado!");
        }

        var user = new User(data);
        user.setCreateDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(data.password()));
        repository.save(user);
    }

    public Authentication authentication(AuthenticationDTO data) {
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        return manager.authenticate(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByContactEmail(username);
    }

    //todo:Testar e criar
    public User updateUser(Long userId, DataUserUpdateDTO data) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Atualizando o contato
        Contact contact = user.getContact();
        if (data.contact() != null) {
            contact.setName(data.contact().name());
            contact.setDepartment(data.contact().department());
            contact.setEmail(data.contact().email()); // Validar se o email já está em uso
            contact.setPhones(data.contact().phones().stream()
                    .map(phoneDTO -> new Phone(phoneDTO.number(), phoneDTO.description()))
                    .collect(Collectors.toList()));
        }

        // Atualizando o endereço
        if (data.address() != null) {
            Address address = user.getAddress();
            address.setStreet(data.address().street());
            address.setNumber(data.address().number());
            address.setNeighborhood(data.address().neighborhood());
            address.setCity(data.address().city());
            address.setState(data.address().state());
            address.setZipCode(data.address().zipCode());
            address.setComplement(data.address().complement());
        }

        // Atualizando o CPF e a data de nascimento
        if (data.cpf() != null) {
            user.setCpf(data.cpf());
        }
        if (data.dateBirth() != null) {
            user.setDateBirth(data.dateBirth());
        }

        // Salvando o usuário com os dados atualizados
        return repository.save(user);
    }

    //todo: testar
    public User updatePassword(Long userId, DataPasswordUpdateDTO data) {
        // Buscar o usuário pelo ID
        User user = repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Passo 1: Verificar se a senha atual está correta
        if (!passwordEncoder.matches(data.currentPassword(), user.getPassword())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        // Passo 2: Verificar se a nova senha é diferente da senha atual
        if (passwordEncoder.matches(data.newPassword(), user.getPassword())) {
            throw new RuntimeException("A nova senha não pode ser a mesma que a senha atual");
        }

        // Passo 3: Verificar se a nova senha e a confirmação da senha são iguais
        if (!data.newPassword().equals(data.confirmPassword())) {
            throw new RuntimeException("A nova senha e a confirmação de senha não coincidem");
        }

        // Passo 4: Atualizar a senha
        user.setPassword(passwordEncoder.encode(data.newPassword()));

        // Salvar o usuário com a nova senha
        return repository.save(user);
    }

}

