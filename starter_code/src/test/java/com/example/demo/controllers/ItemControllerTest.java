package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest  {
private ItemController itemController;
private ItemRepository itemRepository = mock(ItemRepository.class);

//source: https://learn.udacity.com/nanodegrees/nd035-ent-elm/parts/cd0629/lessons/40dec9de-ebe0-4312-b924-d7fd256e1c8f/concepts/fbb3afc7-cb55-4947-b746-d03367db1c07
    //https://github.com/udacity/nd035-c4-Security-and-DevOps/tree/3.UnitTestSolution/TestingSolution/src/test/java/com/udacity/examples/Testing

    @Before
public void setUp(){
    itemController = new ItemController(null);
    TestUtils.injectObjects(itemController,"itemRepository",itemRepository);
Item item = new Item();
item.setId(1L);
item.setName("Example");
item.setDescription("Example description");
item.setPrice(BigDecimal.valueOf(3.56));

    when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
    when(itemRepository.findByName("Example")).thenReturn(Collections.singletonList(item));
    when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item));

}
@Test
    public void get_All_Items() {
        //saving the returned value of "getItems"
        ResponseEntity<List<Item>> allItems = itemController.getItems();
        assertNotNull(allItems);
        assertEquals(200,allItems.getStatusCodeValue());
    }
@Test
    public void get_Item_By_Id() {
        ResponseEntity<Item> item = itemController.getItemById(1L);
        assertNotNull(item);
        assertEquals(200,item.getStatusCodeValue());
    }
@Test
    public void get_Items_By_Name() {
        ResponseEntity<List<Item>> item = itemController.getItemsByName("Example");
        assertNotNull(item);
        assertEquals(200,item.getStatusCodeValue());
    }
}