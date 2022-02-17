package pl.gm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gm.project.model.Quest;

public interface QuestRepository extends JpaRepository<Quest,Long> {
}
