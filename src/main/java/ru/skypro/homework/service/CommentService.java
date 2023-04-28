package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.model.CommentEntity;

public interface CommentService {
    void deleteComment(Integer adId, Integer commentId);

    Comment updateComment(Integer adId, Integer commentId, Comment comment);

    Comment addComment(Integer id, Comment comment, Authentication authentication);

    ResponseWrapperComment getComments(Integer id);

    CommentEntity getByIdAndAdId(Integer commentId, Integer adId);
    CommentEntity getById(Integer commentId);
}
