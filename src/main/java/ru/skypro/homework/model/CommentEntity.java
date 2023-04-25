package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private Instant createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity author;
    @ManyToOne(fetch = FetchType.LAZY)
    private Ad ad;
}
