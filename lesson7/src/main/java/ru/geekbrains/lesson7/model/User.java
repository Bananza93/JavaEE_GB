package ru.geekbrains.lesson7.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = "roles")
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "created_at")
    private Instant createdTime;

    @Column(name = "is_enable")
    private boolean isEnable;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}
