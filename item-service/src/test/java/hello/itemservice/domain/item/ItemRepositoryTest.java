package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        Item item = new Item("itemA", 3000, 10);

        Item savedItem = itemRepository.save(item);

        Item findItem = itemRepository.findById(savedItem.getId());

        assertThat(findItem).isEqualTo(savedItem);

    }

    @Test
    void findAll(){
        Item item1 = new Item("itemA", 3000, 10);
        Item item2 = new Item("itemB", 5000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        List<Item> result = itemRepository.findAll();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);

    }

    @Test
    void updateItem(){
        Item item = new Item("itemA", 3000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("itemB", 6000, 20);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

    }

}