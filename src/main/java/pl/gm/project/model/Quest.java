package pl.gm.project.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "quests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private int mobCountToKill;
    private int killedMob;
    private int experiencePrize;
    private int goldPrize;
    @OneToOne
    @JoinColumn(name = "mob_id")
    private Mob mob;
}
