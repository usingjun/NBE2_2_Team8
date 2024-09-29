//package edu.example.learner.comment.entity;
//
//import edu.example.learner.course.news.entity.NewsEntity;
//import edu.example.learner.entity.Member;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Getter
//public class Comment extends JpaBaseTimeEntity {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comment_id")
//    private Long id;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member; // 댓글 작성자
//
//    @Column(length = 1000)
//    private String textBody; // 댓글 본문
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "news_id")
//    private NewsEntity parentPost; // 댓글 달린 소식
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_comment_id")
//    private Comment parentComment; // 댓글 달린 댓글(부모 댓글)
//
//
//
//    private Boolean isCommentForComment; // 대댓글 여부
//    private LocalDateTime deletedTime; //삭제시간
//    private Boolean deletedTrue; //삭제 여부
//    private Integer depth; // 댓글의 깊이
//    private Long orderNumber; //댓글 순서
//
//    public Comment(String textBody, NewsEntity parentPost, Comment parentComment, Member member) {
//        this.textBody = textBody;
//        this.parentPost = parentPost;
//        this.parentComment = parentComment;
//        this.member = member;
//        this.deletedTime = null;
//        this.deletedTrue = false;
//        if (parentComment == null) { // 부모 댓글이 없을때
//            this.isCommentForComment = false; // 대댓글 아님
//            this.depth = 0;
//            this.orderNumber = parentPost.getCommentCnt();
//        } else { // 부모 댓글이 존재할때
//            this.isCommentForComment = true; // 대댓글
//            this.depth = parentComment.getDepth() + 1;
//            this.orderNumber = parentComment.getOrderNumber();
//        }
//    }
//
//    public void updateTextBody(String textBody) {
//        this.textBody = textBody;
//    }
//
//    public void deleteComment() {
//        this.deletedTrue = true;
//        this.deletedTime = LocalDateTime.now();
//    }
//}
