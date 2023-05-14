package com.luxurydecor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luxurydecor.entities.Blog;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer>{

}
