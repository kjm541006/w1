package study.project1.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import study.project1.domain.Item;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryImplTest {

    ItemRepositoryImpl itemRepository = new ItemRepositoryImpl();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    public void saveAndFind(){
        Item item = new Item("item A", 10000, 20);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.find(savedItem.getId());

        assertThat(savedItem).isEqualTo(findItem);
    }

    @Test
    public void findAll(){
        Item item1 = new Item("item A", 10000, 20);
        Item item2 = new Item("item B", 20000, 10);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> findItems = itemRepository.findAll();

        assertThat(findItems.size()).isEqualTo(2);
    }

    @Test
    public void update(){
        Item item = new Item("item A", 10000, 20);

        Item savedItem = itemRepository.save(item);
        Long savedId = savedItem.getId();

        Item updateParam = new Item("item B", 2000, 40);
        itemRepository.update(savedId, updateParam);

        Item findItem = itemRepository.find(savedId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }
}