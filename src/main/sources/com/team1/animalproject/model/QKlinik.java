package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QKlinik is a Querydsl query type for Klinik
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QKlinik extends EntityPathBase<Klinik> {

    private static final long serialVersionUID = 1009669285L;

    public static final QKlinik klinik = new QKlinik("klinik");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    public final StringPath phone = createString("phone");

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public QKlinik(String variable) {
        super(Klinik.class, forVariable(variable));
    }

    public QKlinik(Path<? extends Klinik> path) {
        super(path.getType(), path.getMetadata());
    }

    public QKlinik(PathMetadata metadata) {
        super(Klinik.class, metadata);
    }

}

