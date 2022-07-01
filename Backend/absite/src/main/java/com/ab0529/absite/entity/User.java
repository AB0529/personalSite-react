package com.ab0529.absite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints ={
        @UniqueConstraint(columnNames =  "username"),
        @UniqueConstraint(columnNames = "email")
})
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NonNull
    private String username;
    @NotBlank
    @NonNull
    @JsonIgnore
    private String password;
    @NotBlank
    @NonNull
    private String firstName;
    @NotBlank
    @NonNull
    private String lastName;
    @NotBlank
    @Email
    @NonNull
    private String email;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(
            name = "users_roles",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
