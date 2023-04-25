package ru.skypro.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity author;
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    @JsonIgnore
    private Collection<CommentEntity> commentEntities;
    private String image;

}
