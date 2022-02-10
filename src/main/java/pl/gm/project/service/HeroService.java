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
import java.util.Random;

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

    public void createHeroForUser(@AuthenticationPrincipal CurrentUserDetails currentUserDetails, Hero hero) {
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

    public void buyItemAndUpdate(Hero hero, Item item) {
        hero.setGold(hero.getGold() - item.getPrice());
        hero.setHealth(hero.getHealth() + item.getIncreaseHealth());
        hero.setMaximumHealth(hero.getMaximumHealth() + item.getIncreaseHealth());
        hero.setMinAttack(hero.getMinAttack() + item.getIncreaseMinAttack());
        hero.setMaxAttack(hero.getMaxAttack() + item.getIncreaseMaxAttack());
        heroRepository.save(hero);
    }

    public void sellItemAndUpdate(Hero hero, Item item) {
        hero.setGold(hero.getGold() + item.getPrice() / 2);
        hero.setMaximumHealth(hero.getMaximumHealth() - item.getIncreaseHealth());
        if (hero.getHealth() > hero.getMaximumHealth()) {
            hero.setHealth(hero.getMaximumHealth());
        }
        hero.setMinAttack(hero.getMinAttack() - item.getIncreaseMinAttack());
        hero.setMaxAttack(hero.getMaxAttack() - item.getIncreaseMaxAttack());
        heroRepository.save(hero);
    }

    public void levelUpifPossible(Hero hero) {
        while (hero.getExperience() >= hero.getExperienceThreshold()) {
            levelUp(hero);
        }
        heroRepository.save(hero);
    }

    public boolean ifPossibleToRaiseStatictic(Hero hero) {
        if (hero.getGold() >= 500) {
            Random random = new Random();
            int rndNumber = random.nextInt(1, 4);
            if (rndNumber == 1) {
                raiseHealthBySacrificingGold(hero, 50);
            }
            if (rndNumber == 2) {
                raiseAttackBySacrificingGold(hero, 2, 4);
            }
            if (rndNumber == 3) {
                raiseExperienceBySacrificingGold(hero, 500);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserHaveHero(Hero hero) {
        return hero != null;
    }

    public static void levelUp(Hero hero) {
        int experienceDifference = hero.getExperience() - hero.getExperienceThreshold();
        hero.setLevel(hero.getLevel() + 1);
        hero.setMinAttack(hero.getMinAttack() + 1);
        hero.setMaxAttack(hero.getMaxAttack() + 2);
        hero.setMaximumHealth(hero.getMaximumHealth() + 20);
        hero.setHealth(hero.getMaximumHealth());
        hero.setExperience(experienceDifference);
        hero.setExperienceThreshold(hero.getExperienceThreshold() + 50);
    }

    public void healByPotion(Hero hero) {
        hero.setHealth(hero.getHealth() + 25);
        hero.setHpPotions(hero.getHpPotions() - 1);
        if (hero.getHealth() > hero.getMaximumHealth()) {
            hero.setHealth(hero.getMaximumHealth());
        }
    }

    public void addHpPotion(Hero hero) {
        hero.setHpPotions(hero.getHpPotions() + 1);
        hero.setGold(hero.getGold() - 20);
    }

    public static void raiseHealthBySacrificingGold(Hero hero, int raiseValue) {
        hero.setGold(hero.getGold() - 500);
        hero.setMaximumHealth(hero.getMaximumHealth() + raiseValue);
        hero.setHealth(hero.getHealth() + raiseValue);
    }

    public static void raiseAttackBySacrificingGold(Hero hero, int minAttackRaiseValue, int maxAttackRaiseValue) {
        hero.setGold(hero.getGold() - 500);
        hero.setMinAttack(hero.getMinAttack() + minAttackRaiseValue);
        hero.setMaxAttack(hero.getMaxAttack() + maxAttackRaiseValue);
    }

    public static void raiseExperienceBySacrificingGold(Hero hero, int experienceValue) {
        hero.setGold(hero.getGold() - 500);
        hero.setExperience(hero.getExperience() + experienceValue);
    }
}