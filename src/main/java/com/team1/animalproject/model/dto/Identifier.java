package com.team1.animalproject.model.dto;

import java.io.Serializable;

public interface Identifier<I extends Serializable> extends Serializable {

	I getId();

	void setId(I id);

}
 