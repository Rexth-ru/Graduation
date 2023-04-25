package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.skypro.homework.model.CommentEntity;

import java.time.Instant;

@Data
public class Comment {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer author; //id author
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authorImage; //ссылка на аватар автора комментария
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authorFirstName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer pk;//id комментария
    private String text;

    public static Comment from(CommentEntity commentEntity) {
        Comment comment = new Comment();
        comment.setAuthor(commentEntity.getAuthor().getId());
        comment.setAuthorImage(commentEntity.getAuthor().getImage());
        comment.setAuthorFirstName(commentEntity.getAuthor().getFirstName());
        comment.setCreatedAt(commentEntity.getCreatedAt().toString());
        comment.setPk(commentEntity.getId());
        comment.setText(commentEntity.getText());
        return comment;
    }

    public CommentEntity to() {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setId(this.getPk());
        commentEntity.setText(this.getText());
        commentEntity.setCreatedAt(Instant.now());
        return commentEntity;
    }
}
