package com.team1.animalproject.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockchainExplorer {

	private String zaman;
	private String hash;
	private String from;
	private String memo;
}


