package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.CommentEntity;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    Optional<CommentEntity> findCommentEntityByIdAndAd_Id(Integer commentId, Integer adId);

    Optional<Collection<CommentEntity>> findAllByAdId(Integer adId);

}
