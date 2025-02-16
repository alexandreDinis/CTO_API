package com.dinis.cto.service;

import com.dinis.cto.dto.person.*;
import com.dinis.cto.infra.MailConfig.EmailService;
import com.dinis.cto.infra.security.SecurityUtils;
import com.dinis.cto.infra.security.TokenService;
import com.dinis.cto.model.person.Address;
import com.dinis.cto.model.person.Contact;
import com.dinis.cto.model.person.Phone;
import com.dinis.cto.model.person.User;
import com.dinis.cto.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
import java.time.format.DateTimeFormatter;
import java.util.Map;
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

        LocalDate dateBirth = convertToDatabaseDateFormat(data.dateBirth());


        var user = new User(data);
        user.setDateBirth(String.valueOf(dateBirth));
        user.setCreateDate(LocalDate.now());
        user.setPassword(passwordEncoder.encode(data.password()));
        repository.save(user);

        //todo: enviar email para verificar conta - criar logica de confirmação.
        emailService.enviarEmail(user.getUsername(),
                "Seja Bem vindo ao cto-app",
                "Voce esta recebendo o email de cadastro");
    }

    // Função para converter a data do formato brasileiro para LocalDate (formato aceito pelo banco)
    private LocalDate convertToDatabaseDateFormat(String dateBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateBirth, formatter);
    }

    public Authentication authentication(AuthenticationDTO data) {
        var token = new UsernamePasswordAuthenticationToken(data.user(), data.password());
        return manager.authenticate(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByContactEmail(username);
    }

    //todo: Testar
    @Transactional
    public DataUserDTO updateUser(HttpServletRequest request, Long userId, DataUserUpdateDTO updateData) {
        // 🔹 Obtém o usuário autenticado (sem acessar o banco novamente)
        User user = SecurityUtils.authenticateAndGetUser(request);

        // 🔹 Agora verifica se ele tem permissão
        SecurityUtils.verifyUserPermission(request, userId);

        // Atualiza os campos básicos do usuário
        if (updateData.cpf() != null) {
            user.setCpf(updateData.cpf());
        }
        if (updateData.dateBirth() != null) {
            user.setDateBirth(updateData.dateBirth());
        }

        // Atualiza o contato (se fornecido)
        if (updateData.contact() != null) {
            updateContact(user.getContact(), updateData.contact());
        }

        // Atualiza o endereço (se fornecido)
        if (updateData.address() != null) {
            updateAddress(user.getAddress(), updateData.address());
        }

        // Salva o usuário (e todas as entidades relacionadas devido ao cascade)
        repository.save(user);

        return new DataUserDTO(user);
    }

    private void updateContact(Contact contact, DataContactDTO contactData) {
        if (contactData.name() != null) {
            contact.setName(contactData.name());
        }
        if (contactData.department() != null) {
            contact.setDepartment(contactData.department());
        }
        if (contactData.email() != null) {
            contact.setEmail(contactData.email());
        }

        // Atualiza os telefones (se fornecido)
        if (contactData.phones() != null) {
            // Mapa para rastrear telefones existentes pelo número
            Map<String, Phone> existingPhones = contact.getPhones().stream()
                    .collect(Collectors.toMap(Phone::getNumber, phone -> phone));

            // Limpa a lista temporariamente para evitar duplicatas
            contact.getPhones().clear();

            // Itera sobre os telefones fornecidos no DTO
            for (DataPhoneDTO phoneData : contactData.phones()) {
                Phone phone = existingPhones.get(phoneData.number());
                if (phone != null) {
                    // Atualiza o telefone existente
                    phone.setDescription(phoneData.description());
                    phone.setNumber(phoneData.number());
                } else {
                    // Cria um novo telefone se não existir
                    phone = new Phone(phoneData.number(), phoneData.description());
                }
                contact.getPhones().add(phone); // Adiciona o telefone à lista do Contact
            }
        }
    }


    private void updateAddress(Address address, DataAddressDTO addressData) {
        if (addressData.street() != null) {
            address.setStreet(addressData.street());
        }
        if (addressData.number() != null) {
            address.setNumber(addressData.number());
        }
        if (addressData.neighborhood() != null) {
            address.setNeighborhood(addressData.neighborhood());
        }
        if (addressData.city() != null) {
            address.setCity(addressData.city());
        }
        if (addressData.state() != null) {
            address.setState(addressData.state());
        }
        if (addressData.zipCode() != null) {
            address.setZipCode(addressData.zipCode());
        }
        if (addressData.complement() != null) {
            address.setComplement(addressData.complement());
        }
    }


    //todo: testar
    public User updatePassword(HttpServletRequest request, DataPasswordUpdateDTO data) {
        // Obter o usuário autenticado
        User user = SecurityUtils.authenticateAndGetUser(request);

        // Verifica se o usuário autenticado tem permissão para alterar a senha (garante que ele está tentando alterar a própria senha)
        SecurityUtils.verifyUserPermission(request, user.getId());

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

