package it.unical.ingsw;

import it.unical.ingsw.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import it.unical.ingsw.dao.UserDao;
import it.unical.ingsw.dto.CreateUserDTO;
import it.unical.ingsw.dto.UserConverter;
import it.unical.ingsw.dto.UserDTO;
import it.unical.ingsw.entities.Role;
import it.unical.ingsw.entities.User;
import it.unical.ingsw.service.EmailService;
import it.unical.ingsw.service.SecurityService;
import it.unical.ingsw.service.UserService;
import it.unical.ingsw.service.UserServiceImpl;
import org.codehaus.plexus.component.configurator.converters.basic.Converter;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    public UserService userService;

    @Mock
    public UserDao userDaoMock;

    @Mock
    public SecurityService securityServiceMock;

    @Mock
    public UserConverter userConverterMock;

    @Mock
    public EmailService emailServiceMock;

    @BeforeAll
    public static void beforeall(){
        System.out.println("INIZIO FASE DI TESTING");
    }

    @AfterAll
    public static void afterall(){
        System.out.println("FINE FASE DI TESTING");
    }

    @BeforeEach
    public void beforeach(){
        System.out.println("INIZIO SINGOLO TEST");
        userService = new UserServiceImpl(userDaoMock, securityServiceMock, emailServiceMock, userConverterMock);
    }

    @AfterEach
    public void aftereach(){
        System.out.println("FINE SINGOLO TEST");
    }

    @Test
    public void createUserTest() throws Exception {
        System.out.println("TEST DELLA FUNZIONE createUser");

        User user = new User("Giuseppe", "Password", "Email");
        UserDTO userDTO = new UserDTO("219911", "Giuseppe", "Email", new Role("Giuseppe", "Description"));

        when(userDaoMock.getUserByEmail(anyString())).thenReturn(null);
        when(userConverterMock.createUserDTOtoUser(any())).thenReturn(user);
        when(securityServiceMock.hash(anyString())).thenReturn(user.getPassword());
        when(userDaoMock.save(any())).thenReturn(user);
        when(userConverterMock.userToUserDTO(any())).thenReturn(userDTO);

        UserDTO userDTO1 = userService.createUser(new CreateUserDTO("Giuseppe", "Password", "Email"));

        assertEquals(userDTO, userDTO1);
    }

}
