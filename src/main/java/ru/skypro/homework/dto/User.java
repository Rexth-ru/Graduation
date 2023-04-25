package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import ru.skypro.homework.en.Role;
import ru.skypro.homework.model.UserEntity;

@Data
public class User {
    private Integer id;
    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private Role role;
    private String firstName;
    private String lastName;
    private String phone;
    private String image;

    public static User from(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setRole(userEntity.getRole());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(user.getLastName());
        user.setPhone(userEntity.getPhone());
        user.setImage(userEntity.getImage());
        return user;
    }
}
