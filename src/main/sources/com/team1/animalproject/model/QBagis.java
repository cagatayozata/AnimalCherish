package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBagis is a Querydsl query type for Bagis
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBagis extends EntityPathBase<Bagis> {

    private static final long serialVersionUID = 578117845L;

    public static final QBagis bagis = new QBagis("bagis");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath description = createString("description");

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final StringPath iban = createString("iban");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public QBagis(String variable) {
        super(Bagis.class, forVariable(variable));
    }

    public QBagis(Path<? extends Bagis> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBagis(PathMetadata metadata) {
        super(Bagis.class, metadata);
    }

}

