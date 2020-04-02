package com.team1.animalproject.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QIpfsID is a Querydsl query type for IpfsID
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QIpfsID extends EntityPathBase<IpfsID> {

    private static final long serialVersionUID = 956019468L;

    public static final QIpfsID ipfsID = new QIpfsID("ipfsID");

    public final com.team1.animalproject.model.dto.QAuditable _super = new com.team1.animalproject.model.dto.QAuditable(this);

    public final StringPath id = createString("id");

    public final StringPath ipfsHash = createString("ipfsHash");

    //inherited
    public final DateTimePath<java.util.Date> olusmaTarihi = _super.olusmaTarihi;

    //inherited
    public final StringPath olusturanKullanici = _super.olusturanKullanici;

    //inherited
    public final DateTimePath<java.util.Date> sonGuncellenmeTarihi = _super.sonGuncellenmeTarihi;

    public QIpfsID(String variable) {
        super(IpfsID.class, forVariable(variable));
    }

    public QIpfsID(Path<? extends IpfsID> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIpfsID(PathMetadata metadata) {
        super(IpfsID.class, metadata);
    }

}

