package com.luxurydecor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luxurydecor.entities.Collection;
@Repository
public interface CollectionRepository extends JpaRepository<Collection, Integer>{
	
}
