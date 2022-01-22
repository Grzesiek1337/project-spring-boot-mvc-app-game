package pl.gm.project.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank
    @Size(min = 3,max=20)
    private String username;

    @NotBlank
    @Size(min = 3)
    private String password;

    private String role;

    private boolean enabled;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "hero_id")
    private Hero hero;


    public Hero getHero() {
        return this.hero;
    }
}
