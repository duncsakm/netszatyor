package ws.ivi.dyndns.netszatyor.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role;

    @Transient
    private String confirmPassword;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserData userData;

    public UserData getUserData() {
        return userData;
    }
}
