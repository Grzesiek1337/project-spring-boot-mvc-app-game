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

    @OneToOne(cascade = { CascadeType.REMOVE, CascadeType.MERGE })
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
