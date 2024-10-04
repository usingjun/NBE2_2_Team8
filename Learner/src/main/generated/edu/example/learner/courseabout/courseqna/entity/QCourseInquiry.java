package edu.example.learner.courseabout.courseqna.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCourseInquiry is a Querydsl query type for CourseInquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourseInquiry extends EntityPathBase<CourseInquiry> {

    private static final long serialVersionUID = 349339803L;

    public static final QCourseInquiry courseInquiry = new QCourseInquiry("courseInquiry");

    public final ListPath<CourseAnswer, QCourseAnswer> answers = this.<CourseAnswer, QCourseAnswer>createList("answers", CourseAnswer.class, QCourseAnswer.class, PathInits.DIRECT2);

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath inquiryContent = createString("inquiryContent");

    public final NumberPath<Long> inquiryGood = createNumber("inquiryGood", Long.class);

    public final NumberPath<Long> inquiryId = createNumber("inquiryId", Long.class);

    public final EnumPath<InquiryStatus> inquiryStatus = createEnum("inquiryStatus", InquiryStatus.class);

    public final StringPath inquiryTitle = createString("inquiryTitle");

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateTime = createDateTime("updateTime", java.time.LocalDateTime.class);

    public QCourseInquiry(String variable) {
        super(CourseInquiry.class, forVariable(variable));
    }

    public QCourseInquiry(Path<? extends CourseInquiry> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseInquiry(PathMetadata metadata) {
        super(CourseInquiry.class, metadata);
    }

}

