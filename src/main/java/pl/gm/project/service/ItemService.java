package pl.gm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gm.project.model.Item;
import pl.gm.project.repository.ItemRepository;

import java.util.List;

@Service
@Transactional
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> listAll() {
        return itemRepository.findAll();
    }

    public void save(Item item) {
        itemRepository.save(item);
    }

    public Item get(long id) {
        return itemRepository.findById(id).get();
    }

    public void update(Item item) {
        itemRepository.save(item);
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}
