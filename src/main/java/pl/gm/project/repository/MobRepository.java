package pl.gm.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gm.project.model.Mob;

public interface MobRepository extends JpaRepository<Mob,Long> {
}
