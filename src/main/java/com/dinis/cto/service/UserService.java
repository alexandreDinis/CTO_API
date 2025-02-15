package com.dinis.cto.service;

import com.dinis.cto.dto.person.*;
import com.dinis.cto.infra.MailConfig.EmailService;
import com.dinis.cto.infra.security.TokenService;
import com.dinis.cto.model.person.Address;
import com.dinis.cto.model.person.Contact;
import com.dinis.cto.model.person.Phone;
import com.dinis.cto.model.person.User;
import com.dinis.cto.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
    private EmailService emailService;

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

        //todo: enviar email para verificar conta - criar logica de confirmação.
        emailService.enviarEmail(user.getUsername(),
                "Seja Bem vindo ao cto-app",
                "Voce esta recebendo o email de cadastro");
    }

    public Authentication authentication(AuthenticationDTO data) {
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        return manager.authenticate(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByContactEmail(username);
    }

    //todo: quando ele atualiza a descricção do phone se tiver appenas um item ele apga o outro e preench com null
    public DataUserDTO updateUser(Long userId, DataUserUpdateDTO data) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        boolean hasChanges = false;

        // Atualiza CPF
        hasChanges |= updateField(data.cpf(), user::getCpf, user::setCpf);

        // Atualiza contato
        if (data.contact() != null) {
            if (user.getContact() == null) {
                user.setContact(new Contact());
                hasChanges = true;
            }

            Contact contact = user.getContact();
            hasChanges |= updateField(data.contact().name(), contact::getName, contact::setName);
            hasChanges |= updateField(data.contact().email(), contact::getEmail, contact::setEmail);
            hasChanges |= updateField(data.contact().department(), contact::getDepartment, contact::setDepartment);

            // Atualiza telefones mantendo os IDs
            if (data.contact().phones() != null) {
                Map<Long, Phone> existingPhones = contact.getPhones().stream()
                        .collect(Collectors.toMap(Phone::getId, phone -> phone));

                for (DataPhoneDTO phoneDTO : data.contact().phones()) {
                    if (phoneDTO.id() != null && existingPhones.containsKey(phoneDTO.id())) {
                        // Atualiza telefone existente
                        Phone phone = existingPhones.get(phoneDTO.id());
                        hasChanges |= updateField(phoneDTO.number(), phone::getNumber, phone::setNumber);
                        hasChanges |= updateField(phoneDTO.description(), phone::getDescription, phone::setDescription);
                    } else {
                        // Adiciona novo telefone se necessário
                        if (phoneDTO.number() != null && phoneDTO.description() != null) {
                            Phone newPhone = new Phone(phoneDTO.number(), phoneDTO.description());
                            contact.getPhones().add(newPhone);
                            hasChanges = true;
                        }
                    }
                }
            }
        }

        // Atualiza endereço
        if (data.address() != null) {
            if (user.getAddress() == null) {
                user.setAddress(new Address());
                hasChanges = true;
            }

            Address address = user.getAddress();
            hasChanges |= updateField(data.address().street(), address::getStreet, address::setStreet);
            hasChanges |= updateField(data.address().city(), address::getCity, address::setCity);
            hasChanges |= updateField(data.address().number(), address::getNumber, address::setNumber);
            hasChanges |= updateField(data.address().complement(), address::getComplement, address::setComplement);
            hasChanges |= updateField(data.address().neighborhood(), address::getNeighborhood, address::setNeighborhood);
            hasChanges |= updateField(data.address().state(), address::getState, address::setState);
            hasChanges |= updateField(data.address().zipCode(), address::getZipCode, address::setZipCode);
        }

        // Retorna DTO atualizado apenas se houve mudanças
        return hasChanges ? new DataUserDTO(repository.save(user)) : new DataUserDTO(user);
    }

    // Método auxiliar genérico para atualizar campos
    private <T> boolean updateField(T newValue, Supplier<T> getter, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(getter.get())) {
            setter.accept(newValue);
            return true;
        }
        return false;
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



//    // Método para enviar o e-mail de confirmação de cadastro
//    public void sendConfirmationEmail(String email) {
//        // Verificar se o e-mail já está cadastrado
//        UserDetails existingUser = repository.findByContactEmail(email);
//        if (existingUser != null) {
//            throw new IllegalArgumentException("E-mail já cadastrado!");
//        }
//
//        // Criar um usuário temporário para gerar o token
//        User tempUser = new User();
//        tempUser.getContact().setEmail(email);
//
//        // Gerar token usando o método existente
//        String token = tokenService.gerarToken(tempUser);
//
//        // Criar e enviar o e-mail com o link de confirmação contendo o token
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Confirmação de Cadastro");
//        message.setText("Clique no link abaixo para confirmar seu cadastro:\n\n"
//                + "http://localhost:8080/confirm?token=" + token);
//
//        // Obtém o email do remetente da configuração do JavaMailSender
//        String remetente = ((JavaMailSenderImpl) mailSender).getUsername();
//        message.setFrom(remetente); // Use o email da conta de serviço
//
//        // Enviar o e-mail
//        mailSender.send(message);
//    }



}

