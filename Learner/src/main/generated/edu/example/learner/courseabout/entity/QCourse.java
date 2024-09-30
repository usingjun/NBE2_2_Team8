package edu.example.learner.courseabout.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import edu.example.learner.courseabout.course.entity.Course;
import edu.example.learner.courseabout.course.entity.CourseAttribute;
import edu.example.learner.courseabout.video.entity.Video;


/**
 * QCourse is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCourse extends EntityPathBase<Course> {

    private static final long serialVersionUID = -2103984852L;

    public static final QCourse course = new QCourse("course");

    public final EnumPath<CourseAttribute> courseAttribute = createEnum("courseAttribute", CourseAttribute.class);

    public final DateTimePath<java.time.LocalDateTime> courseCreatedDate = createDateTime("courseCreatedDate", java.time.LocalDateTime.class);

    public final StringPath courseDescription = createString("courseDescription");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final NumberPath<Integer> courseLevel = createNumber("courseLevel", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> courseModifiedDate = createDateTime("courseModifiedDate", java.time.LocalDateTime.class);

    public final StringPath courseName = createString("courseName");

    public final NumberPath<Long> coursePrice = createNumber("coursePrice", Long.class);

    public final StringPath instructorName = createString("instructorName");

    public final ListPath<edu.example.learner.courseabout.news.entity.NewsEntity, edu.example.learner.courseabout.news.entity.QNewsEntity> newsEntities = this.<edu.example.learner.courseabout.news.entity.NewsEntity, edu.example.learner.courseabout.news.entity.QNewsEntity>createList("newsEntities", edu.example.learner.courseabout.news.entity.NewsEntity.class, edu.example.learner.courseabout.news.entity.QNewsEntity.class, PathInits.DIRECT2);

    public final BooleanPath sale = createBoolean("sale");

    public final ListPath<Video, QVideo> videos = this.<Video, QVideo>createList("videos", Video.class, QVideo.class, PathInits.DIRECT2);

    public QCourse(String variable) {
        super(Course.class, forVariable(variable));
    }

    public QCourse(Path<? extends Course> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourse(PathMetadata metadata) {
        super(Course.class, metadata);
    }

}

