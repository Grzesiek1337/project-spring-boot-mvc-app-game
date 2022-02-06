package pl.gm.project.model;


import lombok.*;

import javax.persistence.*;

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


}
