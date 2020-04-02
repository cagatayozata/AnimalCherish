package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGercekKisi is a Querydsl query type for GercekKisi
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QGercekKisi extends EntityPathBase<GercekKisi> {

    private static final long serialVersionUID = -1723137402L;

    public static final QGercekKisi gercekKisi = new QGercekKisi("gercekKisi");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath ad = createString("ad");

    public final StringPath adresi = createString("adresi");

    public final DateTimePath<java.util.Date> dogumTarihi = createDateTime("dogumTarihi", java.util.Date.class);

    public final StringPath id = createString("id");

    public final StringPath kimlikNo = createString("kimlikNo");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public final StringPath telefon = createString("telefon");

    public QGercekKisi(String variable) {
        super(GercekKisi.class, forVariable(variable));
    }

    public QGercekKisi(Path<? extends GercekKisi> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGercekKisi(PathMetadata metadata) {
        super(GercekKisi.class, metadata);
    }

}

