package edu.example.learner.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import edu.example.learner.comment.entity.Comment;
import edu.example.learner.comment.entity.QComment;
import edu.example.learner.course.entity.QNewsEntity;
import edu.example.learner.entity.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static edu.example.learner.comment.entity.QComment.*;
import static edu.example.learner.course.entity.QNewsEntity.*;
import static edu.example.learner.entity.QMember.*;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> findPureCommentListByPost(Long postId, Pageable pageable) {
        QComment parentComment = new QComment("parentComment");
        QMember parentMember = new QMember("parentMember");
        List<Comment> fetch = queryFactory
                .select(comment)
                .from(comment)
                .join(comment.parentPost, newsEntity).fetchJoin()
                .join(comment.member, member).fetchJoin()
                .leftJoin(comment.parentComment, parentComment).fetchJoin()
                .where(comment.parentPost.newsId.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.orderNumber.asc(), comment.createdDate.asc())
                .fetch();
        int size = queryFactory
                .selectFrom(comment)
                .where(comment.parentPost.newsId.eq(postId))
                .fetch().size();
        return new PageImpl<>(fetch, pageable, size);
    }
}
