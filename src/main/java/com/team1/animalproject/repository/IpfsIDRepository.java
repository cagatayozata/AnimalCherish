package com.team1.animalproject.repository;

import com.team1.animalproject.model.IpfsID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("ipfsIDRepository")
public interface IpfsIDRepository extends JpaRepository<IpfsID, String> {

	List<IpfsID> findByIdIn(List<String> ids);
}
