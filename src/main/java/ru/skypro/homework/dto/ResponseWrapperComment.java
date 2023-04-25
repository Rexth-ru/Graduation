package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.CommentEntity;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private Collection<Comment> results;

    public static ResponseWrapperComment from(Collection<CommentEntity> commentEntities) {
        ResponseWrapperComment responseWrapperComment = new ResponseWrapperComment();
        responseWrapperComment.setResults(commentEntities.stream().map(Comment::from)
                .collect(Collectors.toList()));
        responseWrapperComment.setCount(commentEntities.size());
        return responseWrapperComment;
    }
}
