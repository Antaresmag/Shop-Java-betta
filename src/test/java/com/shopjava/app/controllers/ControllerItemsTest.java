
package com.shopjava.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.services.ShopItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ShopItemController.class)
public class ControllerItemsTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShopItemService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getAllItems() throws Exception {
        var newItem = new ShopItem(
                "test item",
                "test description",
                0.65, 196d
        );
        var list = List.of(newItem, newItem);

        String expectedJson = mapper.writeValueAsString(new ApiResponse<>(list));

        given(service.getAllItems()).willReturn(list);
        mvc.perform(get("/items/").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(expectedJson));
    }

    @Test
    public void getItemById() throws Exception {
        var newItem = new ShopItem(
                "test item",
                "test description",
                0.65, 196d
        );
        newItem.setId(UUID.randomUUID());

        var response = new ApiResponse.Builder<ShopItem>()
            .setResponse(newItem).build();
        String responseJson = mapper.writeValueAsString(response);

        given(service.getItemById(newItem.getId()))
            .willReturn(Optional.of(newItem));
        mvc.perform(get("/items/" + newItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void createItem() throws Exception {
        var newItem = new ShopItem(
                "test item",
                "test description",
                0.65, 196d
        );
        var response = new ApiResponse<>(true, null, true);

        String content = mapper.writeValueAsString(newItem);
        String responseJson = mapper.writeValueAsString(response);

        mvc.perform(
            post("/items/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void updateItem() throws Exception {
        var itemId = UUID.randomUUID();

        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        String newItemJson = mapper.writeValueAsString(newItem);

        var response = new ApiResponse<>(true, null, true);
        String responseJson = mapper.writeValueAsString(response);

        given(service.updateItem(itemId, newItem)).willReturn(true);

        mvc.perform(
            post("/items/" + itemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newItemJson))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }

    @Test
    public void deleteItem() throws Exception {
        var itemId = UUID.randomUUID();

        var response = new ApiResponse<>(true, null, true);
        String responseJson = mapper.writeValueAsString(response);

        given(service.deleteItem(itemId)).willReturn(true);

        mvc.perform(
            delete("/items/" + itemId))
            .andExpect(status().isOk())
            .andExpect(content().json(responseJson));
    }
}

//var itemContainer = Optional.of(newItem);
//given(service.getItemById(itemId)).willReturn(itemContainer);
//given(service.updateItem(BDDMockito.eq(itemId), BDDMockito.eq(newItem))).willReturn(true);