package study.project1.repository;

import study.project1.domain.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository{

    private Map<Long, Item> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);

        return item;
    }

    @Override
    public Item find(Long id) {
        Item item = store.get(id);

        return item;
    }

    @Override
    public List<Item> findAll() {

        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long id, Item updateParam) {
        Item findItem = store.get(id);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}
