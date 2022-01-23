package pl.gm.project.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int attack;
    private int health;
    private int level;
    private int experience;
    private int gold;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hero_items", joinColumns = @JoinColumn(name = "hero_id"),
            inverseJoinColumns = @JoinColumn(name = "items_id"))
    private List<Item> items = new ArrayList<>();

    public String getUserName() {
        if (this.user == null) {
            return "no user";
        } else {
            return this.user.getUsername();
        }
    }
}
