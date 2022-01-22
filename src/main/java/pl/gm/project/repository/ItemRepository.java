package pl.gm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gm.project.model.Item;


public interface ItemRepository extends JpaRepository<Item, Long> {

}
