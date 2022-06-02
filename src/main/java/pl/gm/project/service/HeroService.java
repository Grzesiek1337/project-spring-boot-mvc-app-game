package pl.gm.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Item;
import pl.gm.project.model.Mob;
import pl.gm.project.model.Quest;
import pl.gm.project.repository.HeroRepository;
import pl.gm.project.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class HeroService {


    private final HeroRepository heroRepository;
    private final UserRepository userRepository;

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
        heroForUser.setDodgeChance(5);
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

    public String buyHpPotion(Hero hero, Model model) {
        if (hero.getGold() >= 20) {
            addHpPotion(hero);
            return "shoppanel";
        } else {
            model.addAttribute("goldAmountNotEnough", "Not enough gold.");
            return "shoppanel";
        }
    }

    public void levelUpifPossible(Hero hero) {
        while (hero.getExperience() >= hero.getExperienceThreshold()) {
            levelUp(hero);
        }
        heroRepository.save(hero);
    }

    public String raiseStatisticsIfPossible(Hero hero, Model model) {
        if (hero.getGold() >= 500) {
            Random random = new Random();
            int rndNumber = random.nextInt(1, 4);
            if (rndNumber == 1) {
                raiseHealthBySacrificingGold(hero, 50);
                model.addAttribute("raiseStatMessage", "Your health increased.");
            }
            if (rndNumber == 2) {
                raiseAttackBySacrificingGold(hero, 1, 2);
                model.addAttribute("raiseStatMessage", "Your attack increased.");
            }
            if (rndNumber == 3) {
                raiseExperienceBySacrificingGold(hero, 500);
                model.addAttribute("raiseStatMessage", "Your attack increased.");
            }
            return "templepanel";
        } else {
            model.addAttribute("goldAmountNotEnought", "Not enough gold.");
            return "templepanel";
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

    public static void regenerateAfterDeath(Hero hero) {
        hero.setHealth(25);
    }

    public void healByPotion(Hero hero) {
        hero.setHealth(hero.getHealth() + 25);
        hero.setHpPotions(hero.getHpPotions() - 1);
        if (hero.getHealth() > hero.getMaximumHealth()) {
            hero.setHealth(hero.getMaximumHealth());
        }
    }

    public static void addHpPotion(Hero hero) {
        hero.setHpPotions(hero.getHpPotions() + 1);
        hero.setGold(hero.getGold() - 20);
    }

    public static void raiseHealthBySacrificingGold(Hero hero, int healthRaiseValue) {
        hero.setGold(hero.getGold() - 500);
        hero.setMaximumHealth(hero.getMaximumHealth() + healthRaiseValue);
        hero.setHealth(hero.getHealth() + healthRaiseValue);
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

    public void checkQuestStatus(Hero hero, Quest quest, Model model) {
        if (quest.getKilledMob() == quest.getMobCountToKill()) {
            hero.setExperience(hero.getExperience() + quest.getExperiencePrize());
            hero.setGold(hero.getGold() + quest.getGoldPrize());
            quest.setKilledMob(0);
            hero.getQuests().remove(quest);
            model.addAttribute("questCompleted", "You have completed your quest. Earned " + quest.getGoldPrize() + " gold and " + quest.getExperiencePrize() + " experience.");
        }
    }

    public boolean checkingDodgeChance(Hero hero) {
        Random random = new Random();
        int randomNumberToCheck = random.nextInt(101);
        return randomNumberToCheck <= hero.getDodgeChance();
    }
    public boolean isHeroHitPointMoreThanZero(Hero hero,Model model) {
        if (hero.getHealth() <= 0) {
            model.addAttribute("failedWonFight", "You have lost this fight.Your health have been regenerated.");
            regenerateAfterDeath(hero);
            return false;
        }
        return true;
    }

    public void getHitFromMonster(Hero hero, Mob mob,Model model) {
        Integer mobAttack = mob.getMobAttackFromMinToMax();
        model.addAttribute("hitHeroMsg", "You have taken  " + mobAttack + " damage from " + mob.getName());
        hero.setHealth(hero.getHealth() - mobAttack);
    }

    public void oneHitToMonster(Hero hero,Mob mob,Model model) {
        Random random = new Random();
        int heroAttack = random.nextInt(hero.getMinAttack(), hero.getMaxAttack() + 1);
        mob.setHealth(mob.getHealth() - heroAttack);
        model.addAttribute("hitMobMsg", "You have attacked " + mob.getName() + " for " + heroAttack + " damege.");

    }

    public void setPrizeAfterFight(Hero hero,Mob mob,Model model) {
        Random random = new Random();
        int earnedGold = random.nextInt(mob.getLevel() * 10, mob.getLevel() * 20);
        int earnedExperience = random.nextInt(mob.getLevel() * 20, mob.getLevel() * 40);
        hero.setGold(hero.getGold() + earnedGold);
        hero.setExperience(hero.getExperience() + earnedExperience);


        model.addAttribute("successWonFightMsg", "You have won the fight!");
        model.addAttribute("earnedGold", "You have earned " + earnedGold + " gold.");
        model.addAttribute("earnedExperience", "You have earned " + earnedExperience + " experience.");
    }
}