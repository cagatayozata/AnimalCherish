package com.team1.animalproject.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Block {

	public String id;
	public String name;
	public String address;
	public Date birthdate;
	public String turId;
	public String cinsId;
	public String turAd;
	public String cinsAd;

}
