package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.ResponseWrapperComment;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.CommentEntity;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, AdRepository adRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.adRepository = adRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        CommentEntity commentEntity = getByIdAndAdId(commentId, adId);
        commentRepository.delete(commentEntity);
    }


    @Override
    public Comment updateComment(Integer adId, Integer commentId, Comment comment) {
        CommentEntity commentEntity = getByIdAndAdId(commentId, adId);
        commentEntity.setText(comment.getText());
        return Comment.from(commentRepository.save(commentEntity));
    }

    @Override
    public Comment addComment(Integer adId, Comment comment, Authentication authentication) {
        CommentEntity commentEntity = comment.to();
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(NotFoundException::new);
        commentEntity.setAd(adRepository.findById(adId).orElseThrow(NotFoundException::new));
        commentEntity.setAuthor(userEntity);
        return Comment.from(commentRepository.save(commentEntity));
    }

    @Override
    public ResponseWrapperComment getComments(Integer id) {
        return ResponseWrapperComment.from(commentRepository.findAllByAdId(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public CommentEntity getByIdAndAdId(Integer commentId, Integer adId) {
        return commentRepository.findCommentEntityByIdAndAd_Id(commentId, adId).orElseThrow(NotFoundException::new);
    }
}
