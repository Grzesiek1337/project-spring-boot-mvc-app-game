package pl.gm.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gm.project.model.Item;
import pl.gm.project.repository.ItemRepository;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> listAll() {
        return itemRepository.findAll();
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item get(long id) {
        return itemRepository.findById(id).get();
    }

    public Item getByName(String name) {
        return itemRepository.getItemByItemname(name);
    }

    public void update(Item item) {
        itemRepository.save(item);
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }

    public void removeItemFromInventory(List<Item> items,String itemName) {
        items.removeIf(item -> item.getName().equals(itemName));
    }
}
