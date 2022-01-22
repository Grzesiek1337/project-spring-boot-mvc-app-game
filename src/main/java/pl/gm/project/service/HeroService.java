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

    public List<Hero> listToChooseForUser() {
        List<Hero> heroes = Arrays.asList(
                new Hero(1L,"Barbarian",5,200,0,0,0,null),
                new Hero(2L,"Assasin",10,150,0,0,0,null),
                new Hero(3L,"Mage",15,100,0,0,0,null)
        );
        return heroes;
    }

    public void save(Hero Hero) {
        heroRepository.save(Hero);
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
