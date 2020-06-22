
package com.okunderenko.app.services;

import com.okunderenko.app.models.ShopItem;
import com.okunderenko.app.models.repositories.ShopItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(ShopItemServiceImpl.class)
public class DataItemsTest {
    @Autowired
    private ShopItemService service;

    @Autowired
    private ShopItemRepository repository;

    @Test
    public void createAndSave() {
        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        var savedItem = service.createItem(newItem);
        var itemId = savedItem.getId();

        Optional<ShopItem> dbItem = service.getItemById(itemId);
        assertThat(dbItem.isPresent()).isTrue();
        assertThat(dbItem.get().getId()).isEqualTo(itemId);
    }

    @Test
    public void getAllItems() {
        service.createItem(new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        ));

        List<ShopItem> allItems = service.getAllItems();
        assertThat(allItems.isEmpty()).isFalse();
    }

    @Test
    public void getItemById() {
        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        var savedItem = service.createItem(newItem);

        UUID targetId = savedItem.getId();
        Optional<ShopItem> targetItem = service.getItemById(targetId);
        assertThat(targetItem.isPresent()).isTrue();
    }

    @Test
    public void updateItem() {
        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        var savedItem = service.createItem(newItem);

        UUID targetId = savedItem.getId();
        Optional<ShopItem> targetItem = service.getItemById(targetId);
        assertThat(targetItem.isPresent()).isTrue();

        var updates = new ShopItem();
        var newDesc = "test description edited";
        updates.setDescription(newDesc);

        boolean updated = service.updateItem(targetId, updates);
        assertThat(updated).isTrue();

        Optional<ShopItem> itemAfterUpdates = service.getItemById(targetId);
        assertThat(itemAfterUpdates.isPresent()).isTrue();

        assertThat(itemAfterUpdates.get().getDescription()).isEqualTo(newDesc);
    }

    @Test
    public void deleteItem() {
        var newItem = new ShopItem(
            "test item",
            "test description",
            0.65, 196d
        );
        var savedItem = service.createItem(newItem);

        UUID itemId = savedItem.getId();
        boolean deleted = service.deleteItem(itemId);
        assertThat(deleted).isTrue();

        var targetItem = service.getItemById(itemId);
        assertThat(targetItem.isPresent()).isFalse();
    }
}

//@ExtendWith(SpringExtension.class)
//    @Autowired
//    private TestEntityManager entityManager;

//@Autowired
//private ShopItemRepository repository;
//@EnableJpaRepositories(basePackages = "com.okunderenko.app.models.repositories")
//@DataJpaTest
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//this.entityManager.persist(newItem);
//var savedItem = repository.save(newItem);
//Optional<ShopItem> dbItem = repository.findById(itemId);

//when(repository.findAll()).thenReturn(fakeList);

//savedIds.add(itemId);
//fakeList.forEach(item -> service.createItem(item));
//UUID itemId = fakeList.get(0).getId();