package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKlinikVet is a Querydsl query type for KlinikVet
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKlinikVet extends EntityPathBase<KlinikVet> {

    private static final long serialVersionUID = 1401781440L;

    public static final QKlinikVet klinikVet = new QKlinikVet("klinikVet");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath id = createString("id");

    public final StringPath klinikId = createString("klinikId");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public final StringPath vetId = createString("vetId");

    public QKlinikVet(String variable) {
        super(KlinikVet.class, forVariable(variable));
    }

    public QKlinikVet(Path<? extends KlinikVet> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKlinikVet(PathMetadata metadata) {
        super(KlinikVet.class, metadata);
    }

}

