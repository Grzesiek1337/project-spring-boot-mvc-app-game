package pl.gm.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int minAttack;
    private int maxAttack;
    private int health;
    private int maximumHealth;
    private int level;
    private int experience;
    private int experienceThreshold;
    private int gold;
    private int hpPotions;
    private int dodgeChance;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "heroes_items", joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "heroes_quests", joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id"))
    private List<Quest> quests = new ArrayList<>();


}
