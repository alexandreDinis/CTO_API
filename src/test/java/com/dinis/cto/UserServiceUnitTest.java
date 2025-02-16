package com.dinis.cto;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.dinis.cto.dto.person.*;
import com.dinis.cto.model.person.Address;
import com.dinis.cto.model.person.Contact;
import com.dinis.cto.model.person.User;
import com.dinis.cto.repository.UserRepository;
import com.dinis.cto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;


public class UserServiceUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
    }

    @Test
    public void testUpdateUser() {
        // Dados de entrada
        Long userId = 1L;
        DataContactDTO contactData = new DataContactDTO(
                "John Doe",
                "IT",
                Collections.singletonList(new DataPhoneDTO(null, "Cellphone", "(11) 9999-9999")),
                "john.doe@example.com"
        );
        DataAddressDTO addressData = new DataAddressDTO(
                "Main St", "123", "Downtown", "Springfield", "IL", "12345-678", "Apt 4B"
        );
        DataUserUpdateDTO updateData = new DataUserUpdateDTO(
                contactData,
                "12345678901",
                "01/01/1990",
                addressData
        );

        // Mock do usuário existente
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setCpf("98765432109");
        existingUser.setDateBirth("01/01/1980");

        // Cria um Contact usando o construtor com DataContactDTO
        DataContactDTO existingContactData = new DataContactDTO(
                "Jane Doe",
                "HR",
                Collections.singletonList(new DataPhoneDTO(null, "Work", "(11) 8888-8888")),
                "jane.doe@example.com"
        );
        Contact existingContact = new Contact(existingContactData);

        // Cria um Address usando o construtor com DataAddressDTO
        DataAddressDTO existingAddressData = new DataAddressDTO(
                "Second St", "456", "Uptown", "Metropolis", "NY", "98765-432", "Apt 7A"
        );
        Address existingAddress = new Address(existingAddressData);

        // Configura o usuário existente
        existingUser.setContact(existingContact);
        existingUser.setAddress(existingAddress);

        // Simula o comportamento do repositório
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Executa o método a ser testado
        DataUserDTO updatedUser = userService.updateUser(userId, updateData);

        // Verificações
        assertNotNull(updatedUser);
        assertEquals("John Doe", updatedUser.contact().name());
        assertEquals("(11) 9999-9999", updatedUser.contact().phones().get(0).number());
        assertEquals("Main St", updatedUser.address().street());
    }
}
