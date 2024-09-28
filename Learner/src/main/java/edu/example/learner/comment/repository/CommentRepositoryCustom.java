package edu.example.learner.comment.repository;

import edu.example.learner.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepositoryCustom {
    Page<Comment> findPureCommentListByPost(Long postId, Pageable pageable);
}
