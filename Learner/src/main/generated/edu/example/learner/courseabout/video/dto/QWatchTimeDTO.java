package edu.example.learner.courseabout.video.dto;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWatchTimeDTO is a Querydsl query type for WatchTimeDTO
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QWatchTimeDTO extends BeanPath<WatchTimeDTO> {

    private static final long serialVersionUID = -918504614L;

    public static final QWatchTimeDTO watchTimeDTO = new QWatchTimeDTO("watchTimeDTO");

    public final NumberPath<Float> currentTime = createNumber("currentTime", Float.class);

    public final NumberPath<Float> duration = createNumber("duration", Float.class);

    public QWatchTimeDTO(String variable) {
        super(WatchTimeDTO.class, forVariable(variable));
    }

    public QWatchTimeDTO(Path<? extends WatchTimeDTO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWatchTimeDTO(PathMetadata metadata) {
        super(WatchTimeDTO.class, metadata);
    }

}

