package pl.gm.project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import pl.gm.project.model.Hero;
import pl.gm.project.model.Mob;
import pl.gm.project.model.Quest;
import pl.gm.project.repository.HeroRepository;
import pl.gm.project.repository.QuestRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private  final HeroRepository heroRepository;

    public List<Quest> listAll() {
        return questRepository.findAll();
    }

    public void save(Quest quest) {
        questRepository.save(quest);
    }

    public Quest get(long id) {
        return questRepository.findById(id).get();
    }

    public void update(Quest quest) {
        questRepository.save(quest);
    }

    public void delete(long id) {
        questRepository.deleteById(id);
    }

    public Quest raiseKilledMobCount(Quest quest) {
        quest.setKilledMob(quest.getKilledMob() + 1);
        return quest;
    }

    public String takeQuestIfPossible(Hero hero, Quest questToPossiblyTake, Model model) {
        List<Quest> quests = hero.getQuests();
        for (Quest quest : hero.getQuests()) {
            if (quest.getDescription().equals(questToPossiblyTake.getDescription())) {
                model.addAttribute("haveQuestAlready", "You already have this quest.");
                return "templepanel";
            }
        }
        quests.add(questToPossiblyTake);
        heroRepository.save(hero);
        return "templepanel";
    }
    public Optional<Quest> findByMob(Mob mob, List<Quest> quests) {
        Quest searchedQuest = null;
        for (Quest quest : quests) {
            if(mob.getName().equals(quest.getMob().getName())) {
                searchedQuest = quest;
            }
        }
        return Optional.ofNullable(searchedQuest);
    }
}
