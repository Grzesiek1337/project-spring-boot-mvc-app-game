package pl.gm.project.model;


import lombok.*;

import javax.persistence.*;
import java.util.Random;

@Entity
@Table(name = "mobs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int minAttack;
    private int maxAttack;
    private int health;
    private int level;

    public Integer getMobAttackFromMinToMax() {
        Random random = new Random();
        return random.nextInt(this.getMinAttack(), this.getMaxAttack() + 1);
    }


}
