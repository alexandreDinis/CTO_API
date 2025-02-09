package com.dinis.cto.service;

import com.dinis.cto.dto.person.AuthenticationDTO;
import com.dinis.cto.dto.person.DataUserDTO;
import com.dinis.cto.dto.report.FirstNameDTO;
import com.dinis.cto.model.person.User;
import com.dinis.cto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    @Lazy
    private AuthenticationManager manager;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
}
