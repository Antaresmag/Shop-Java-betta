
package com.shopjava.app.controllers;

import com.shopjava.app.models.ShopItem;
import com.shopjava.app.models.rest.ApiResponse;
import com.shopjava.app.services.ShopItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class ShopItemController {
    @Autowired
    ShopItemService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<ShopItem>>> getAllItems() {
        List<ShopItem> items = service.getAllItems();
        ApiResponse<List<ShopItem>> response =
            new ApiResponse.Builder<List<ShopItem>>()
                .setSuccess(true).setResponse(items).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ShopItem>> getItemById(@PathVariable String id) {
        Optional<ShopItem> item = service.getItemById(UUID.fromString(id));

        var response = new ApiResponse.Builder<ShopItem>();
        response.setSuccess(item.isPresent());

        if (item.isPresent()) {
            response.setResponse(item.get());
        }
        else {
            response.setErrorMessage("Not found");
        }

        return new ResponseEntity<>(response.build(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Boolean>> createItem(
        @RequestBody ShopItem requestEntity
    ) {
        service.createItem(requestEntity);
        var response = new ApiResponse.Builder<Boolean>()
            .setSuccess(true).setResponse(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> updateItem(
        @PathVariable String id,
        @RequestBody ShopItem requestEntity
    ) {
        var itemId = UUID.fromString(id);
        boolean updated = service.updateItem(itemId, requestEntity);

        var responseBuilder = new ApiResponse.Builder<Boolean>()
            .setSuccess(updated).setResponse(updated);
        if (!updated) {
            responseBuilder.setErrorMessage("Item not found");
        }
        return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteItem(@PathVariable String id) {
        boolean deleted = service.deleteItem(UUID.fromString(id));

        var responseBuilder = new ApiResponse.Builder<Boolean>()
            .setSuccess(deleted).setResponse(deleted);
        if (!deleted) {
            responseBuilder.setErrorMessage("Item not found");
        }
        return new ResponseEntity<>(responseBuilder.build(), HttpStatus.OK);
    }
}
