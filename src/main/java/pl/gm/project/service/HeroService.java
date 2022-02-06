package pl.gm.project.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Item;
import pl.gm.project.repository.HeroRepository;
import pl.gm.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;
    private UserRepository userRepository;

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

    public void createHeroForUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails,Hero hero) {
        Hero heroForUser = new Hero();
        heroForUser.setName(hero.getName());
        heroForUser.setMinAttack(1);
        heroForUser.setMaxAttack(5);
        heroForUser.setHealth(100);
        heroForUser.setMaximumHealth(100);
        heroForUser.setLevel(1);
        heroForUser.setExperience(0);
        heroForUser.setExperienceThreshold(100);
        heroForUser.setGold(100);
        heroForUser.setHpPotions(5);
        heroForUser.setUser(currentUserDetails.getUser());
        heroForUser.setItems(new ArrayList<Item>());
        currentUserDetails.setHero(heroForUser);
        heroRepository.save(heroForUser);
        userRepository.save(currentUserDetails.getUser());
    }

    public void buyItemAndUpdate(Hero hero , Item item) {
        hero.setGold(hero.getGold() - item.getPrice());
        hero.setHealth(hero.getHealth() + item.getIncreaseHealth());
        hero.setMaximumHealth(hero.getMaximumHealth() + item.getIncreaseHealth());
        hero.setMinAttack(hero.getMinAttack() + item.getIncreaseMinAttack());
        hero.setMaxAttack(hero.getMaxAttack() + item.getIncreaseMaxAttack());
        heroRepository.save(hero);
    }

    public void sellItemAndUpdate(Hero hero , Item item) {
        hero.setGold(hero.getGold() + item.getPrice()/2);
        hero.setMaximumHealth(hero.getMaximumHealth() - item.getIncreaseHealth());
        if(hero.getHealth() > hero.getMaximumHealth()) {
            hero.setHealth(hero.getMaximumHealth());
        }
        hero.setMinAttack(hero.getMinAttack() - item.getIncreaseMinAttack());
        hero.setMaxAttack(hero.getMaxAttack() - item.getIncreaseMaxAttack());
        heroRepository.save(hero);
    }

    public void levelUp(Hero hero) {
        hero.setLevel(hero.getLevel() + 1);
        hero.setMinAttack(hero.getMinAttack() + 1);
        hero.setMaxAttack(hero.getMaxAttack() + 2);
        hero.setMaximumHealth(hero.getMaximumHealth() + 20);
        hero.setHealth(hero.getMaximumHealth());
        hero.setExperience(0);
        hero.setExperienceThreshold(hero.getExperienceThreshold() + 50);
        heroRepository.save(hero);
    }
}
