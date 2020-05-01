package com.team1.animalproject.view;

import com.team1.animalproject.model.BlockchainExplorer;
import com.team1.animalproject.service.BlockchainService;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Component
@Scope ("view")
@EqualsAndHashCode (callSuper = false)
@Data
public class BlockchainBean extends BaseViewController<BlockchainExplorer> implements Serializable {

	private static final long serialVersionUID = -5486608545743134294L;

	private static final Logger logger = LoggerFactory.getLogger(BlockchainBean.class);

	@Autowired
	private BlockchainService blockchainService;

	private List<BlockchainExplorer> blockchainExplorers;
	private BlockchainExplorer selected;

	@Override
	@PostConstruct
	public void viewOlustur() throws IOException {
		blockchainExplorers = blockchainService.explorer();
	}

	@Override
	public void ilkEkraniHazirla() {
		try{
			blockchainExplorers = blockchainService.explorer();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
