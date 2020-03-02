package com.team1.animalproject.repository.custom;

import com.team1.animalproject.model.Cins;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomCinsRepository {

	List<Cins> cinsAra();
}
