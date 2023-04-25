package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.skypro.homework.en.Role;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;//логин
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    private Boolean enabled;
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Collection<Ad> adCollection;
    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Collection<CommentEntity> commentEntityCollection;
}
