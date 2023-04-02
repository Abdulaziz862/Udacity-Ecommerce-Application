package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
private CartController cartController;
private UserRepository userRepository= mock(UserRepository.class);
private CartRepository cartRepository= mock(CartRepository.class);
private ItemRepository itemRepository = mock(ItemRepository.class);
//source: https://learn.udacity.com/nanodegrees/nd035-ent-elm/parts/cd0629/lessons/40dec9de-ebe0-4312-b924-d7fd256e1c8f/concepts/fbb3afc7-cb55-4947-b746-d03367db1c07
    //https://github.com/udacity/nd035-c4-Security-and-DevOps/tree/3.UnitTestSolution/TestingSolution/src/test/java/com/udacity/examples/Testing
    @Before
    public void setUp() {
        cartController = new CartController(null, null, null);
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0);
        user.setUsername("Example");
        user.setPassword("ExamplePwd");
        user.setCart(cart);

        when(userRepository.findByUsername("Example")).thenReturn(user);

        Item item = new Item();
        item.setId(1L);
        item.setName("Example Item");
        BigDecimal price = BigDecimal.valueOf(3.56);
        item.setPrice(price);
        item.setDescription("Example Item Description");
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

    }
@Test
    public void add_To_cart_success() {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("Example");
        request.setQuantity(1);
        request.setItemId(1L);

        ResponseEntity<Cart> response= cartController.addTocart(request);
        Cart cart = response.getBody();
        Assert.assertNotNull(request);
       Assert.assertNotNull(cart);
        //6.45
       Assert.assertEquals(BigDecimal.valueOf(3.56), cart.getTotal());
       Assert.assertEquals(200, response.getStatusCodeValue());

    }
    @Test
    public void add_To_cart_invalid_User() {
        ModifyCartRequest request = new ModifyCartRequest();
        //none-existing username
        request.setUsername("test");
        request.setQuantity(1);
        request.setItemId(1L);

        ResponseEntity<Cart> response= cartController.addTocart(request);

        Assert.assertNotNull(request);
        //404 = Not Found response
        Assert.assertEquals(404, response.getStatusCodeValue());

    }
    @Test
    public void add_To_cart_invalid_Item_Id() {
        ModifyCartRequest request = new ModifyCartRequest();
        //none-existing username
        request.setUsername("test");
        request.setQuantity(1);
        request.setItemId(2L);

        ResponseEntity<Cart> response= cartController.addTocart(request);

        Assert.assertNotNull(request);
        //404 = Not Found response
        Assert.assertEquals(404, response.getStatusCodeValue());

    }
    @Test
    public void remove_Item_From_cart() {
        ModifyCartRequest request = new ModifyCartRequest();
        //none-existing username
        request.setUsername("example");
        request.setQuantity(1);
        request.setItemId(2L);

        ResponseEntity<Cart> response= cartController.addTocart(request);

        Assert.assertNotNull(request);
    }
}