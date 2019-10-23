package com.sports.frontend.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sports.frontend.db.bean.Club;

public interface ClubesRepository extends JpaRepository<Club,Long>{

	@Query(value="select bm.* from Clubes bm",nativeQuery=true)
	List<Club> listarClubes();

	@Query("SELECT c FROM Club c")
	List<Club> findAllClubs();
	
}
