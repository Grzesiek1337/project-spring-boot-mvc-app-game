package pl.gm.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Mob;
import pl.gm.project.model.Quest;

import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class FightService {

    private final HeroService heroService;
    private final QuestService questService;
    private final MobService mobService;

    public String oneTurnHeroVsMob(Hero hero,Mob mob, Model model) {
        Random random = new Random();
        int heroAttack = random.nextInt(hero.getMinAttack(), hero.getMaxAttack() + 1);
        int mobAttack = random.nextInt(mob.getMinAttack(), mob.getMaxAttack() + 1);
        mob.setHealth(mob.getHealth() - heroAttack);
        int randomNumberToCheck = random.nextInt(101);
        if(randomNumberToCheck <= hero.getDodgeChance()) {
            model.addAttribute("dodgeMessage", "A hit was dodged.");

        } else {
            hero.setHealth(hero.getHealth() - mobAttack);
            model.addAttribute("hitHeroMsg", "You have taken  " + mobAttack + " damage from " + mob.getName());

        }
        heroService.update(hero);
        if (hero.getHealth() <= 0) {
            model.addAttribute("failedWonFight", "You have lost this fight.Your health have been little regenerated.");
            heroService.regenerateAfterDeath(hero);
            return "gamepanel";
        }
        if (mob.getHealth() > 0) {
            model.addAttribute("hitMobMsg", "You have attacked " + mob.getName() + " for " + heroAttack + " damege.");
            model.addAttribute("mob", mob);
            return "fightpanel";
        } else {
            Optional<Quest> quest = questService.findByMob(mob,hero.getQuests());
            if(quest.isPresent()) {
                questService.raiseKilledMobCount(quest.orElse(null));
                heroService.checkQuestStatus(hero,quest.orElse(null),model);
            }
            int earnedGold = random.nextInt(mob.getLevel() * 10, mob.getLevel() * 20);
            int earnedExperience = random.nextInt(mob.getLevel() * 20, mob.getLevel() * 40);
            hero.setGold(hero.getGold() + earnedGold);
            hero.setExperience(hero.getExperience() + earnedExperience);
            heroService.update(hero);
            heroService.levelUpifPossible(hero);
            model.addAttribute("successWonFightMsg", "You have won the fight!");
            model.addAttribute("earnedGold", "You have earned " + earnedGold + " gold.");
            model.addAttribute("earnedExperience", "You have earned " + earnedExperience + " experience.");
            return "gamepanel";
        }
    }
}
