package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest  {
   // @Autowired
 OrderController orderController;
private OrderRepository orderRepository = mock(OrderRepository.class);
private UserRepository userRepository = mock(UserRepository.class);
@Before
public void setup(){
    orderController = new OrderController(null,null);
    TestUtils.injectObjects(orderController,"orderRepository",orderRepository);
    TestUtils.injectObjects(orderController,"userRepository",userRepository);

    UserOrder order = new UserOrder();
    Item item = new Item();
    User user = new User();
    List<Item> items = new ArrayList<>();
    Cart cart = new Cart();

    cart.setId(1L);
    cart.setItems(items);
    cart.setTotal(BigDecimal.valueOf(3.56));
    cart.setUser(user);

    user.setId(1L);
    user.setPassword("Example");
    user.setUsername("Example");
    user.setCart(cart);
    item.setPrice(BigDecimal.valueOf(3.56));
    item.setName("Example");
    item.setId(1L);
    item.setDescription("Example Description");
    items.add(item);

    order.setId(1L);
    order.setItems(items);
    order.setUser(user);
    order.setTotal(BigDecimal.valueOf(3.56));
    when(userRepository.findByUsername("Example")).thenReturn(user);




}

    @Test
    public void test_Submit() {
        ResponseEntity<UserOrder> submitResponse = orderController.submit("Example");
        assertNotNull(submitResponse);
        assertEquals(200, submitResponse.getStatusCodeValue());
    }
@Test
    public void testGetOrdersForUser() {
    ResponseEntity<List<UserOrder>> userOrders = orderController.getOrdersForUser("Example");
    assertNotNull(userOrders);
    assertEquals(200,userOrders.getStatusCodeValue());
    }
}