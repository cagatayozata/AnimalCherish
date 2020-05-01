package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVetRandevu is a Querydsl query type for VetRandevu
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVetRandevu extends EntityPathBase<VetRandevu> {

    private static final long serialVersionUID = 796082295L;

    public static final QVetRandevu vetRandevu = new QVetRandevu("vetRandevu");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath id = createString("id");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    public final StringPath randevuAlanKullanici = createString("randevuAlanKullanici");

    public final DateTimePath<java.util.Date> randevuTarihi = createDateTime("randevuTarihi", java.util.Date.class);

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public final StringPath vetId = createString("vetId");

    public QVetRandevu(String variable) {
        super(VetRandevu.class, forVariable(variable));
    }

    public QVetRandevu(Path<? extends VetRandevu> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVetRandevu(PathMetadata metadata) {
        super(VetRandevu.class, metadata);
    }

}

