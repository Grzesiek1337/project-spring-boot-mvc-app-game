package pl.gm.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gm.project.model.Mob;
import pl.gm.project.repository.MobRepository;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MobService {

    @Autowired
    private MobRepository mobRepository;

    public List<Mob> listAll() {
        return mobRepository.findAll();
    }

    public void save(Mob mob) {
        mobRepository.save(mob);
    }

    public Mob get(long id) {
        return mobRepository.findById(id).get();
    }

    public void update(Mob mob) {
        mobRepository.save(mob);
    }

    public void delete(long id) {
        mobRepository.deleteById(id);
    }
}
