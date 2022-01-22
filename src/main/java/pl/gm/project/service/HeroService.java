package pl.gm.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gm.project.model.Hero;
import pl.gm.project.repository.HeroRepository;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    public List<Hero> listAll() {
        return heroRepository.findAll();
    }

    public void save(Hero hero) {
        heroRepository.save(hero);
    }

    public Hero get(long id) {
        return heroRepository.findById(id).get();
    }

    public void update(Hero hero) {
        heroRepository.save(hero);
    }

    public void delete(long id) {
        heroRepository.deleteById(id);
    }
}
