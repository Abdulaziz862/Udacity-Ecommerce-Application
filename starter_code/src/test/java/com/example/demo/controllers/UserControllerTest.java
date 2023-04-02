package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest  {
private UserController userController;

private UserRepository userRepository = mock(UserRepository.class);
private CartRepository cartRepository = mock(CartRepository.class);
private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);



    @Before
    public void setUp(){
        userController = new UserController(null,null,null);
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    User user = new User();
    Cart cart = new Cart();
    Item item = new Item();
    List<Item> items = new ArrayList<>();


    item.setPrice(BigDecimal.valueOf(3.56));
    item.setName("Example");
    item.setId(1L);
    item.setDescription("Example Description");
    items.add(item);

    user.setId(1L);
    user.setPassword("Example");
    user.setUsername("Example");
    user.setCart(cart);

    cart.setUser(user);
    cart.setTotal(BigDecimal.valueOf(3.56));
    cart.setItems(items);
    cart.setId(1L);
        when(userRepository.findByUsername("Example")).thenReturn(user);
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

    }
    @Test
    public void Find_By_Id() {
        ResponseEntity<User> userResponse = userController.findById(1L);
        assertNotNull(userResponse);
        assertEquals(200,userResponse.getStatusCodeValue());
    }
@Test
    public void Find_By_UserName() {
    ResponseEntity<User> userResponse = userController.findByUserName("Example");
    assertNotNull(userResponse);
    assertEquals(200,userResponse.getStatusCodeValue());
    }
@Test
    public void Create_User() {
        CreateUserRequest createUserRequestUser= new CreateUserRequest();
        createUserRequestUser.setUsername("Example");
        ResponseEntity<User> userResponse = userController.createUser(createUserRequestUser);
        assertNotNull(userResponse);
        assertEquals(200,userResponse.getStatusCodeValue());
    }
}