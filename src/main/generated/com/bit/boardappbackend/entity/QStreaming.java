package com.bit.boardappbackend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStreaming is a Querydsl query type for Streaming
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStreaming extends EntityPathBase<Streaming> {

    private static final long serialVersionUID = -324285030L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStreaming streaming = new QStreaming("streaming");

    public final QBoard board;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> streamEndTime = createDateTime("streamEndTime", java.time.LocalDateTime.class);

    public final StringPath streamKey = createString("streamKey");

    public final DateTimePath<java.time.LocalDateTime> streamStartTime = createDateTime("streamStartTime", java.time.LocalDateTime.class);

    public final StringPath streamUrl = createString("streamUrl");

    public QStreaming(String variable) {
        this(Streaming.class, forVariable(variable), INITS);
    }

    public QStreaming(Path<? extends Streaming> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStreaming(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStreaming(PathMetadata metadata, PathInits inits) {
        this(Streaming.class, metadata, inits);
    }

    public QStreaming(Class<? extends Streaming> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

