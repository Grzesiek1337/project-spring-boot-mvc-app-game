package pl.gm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gm.project.model.Hero;

public interface HeroRepository extends JpaRepository<Hero, Long> {

}
