package com.accolite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accolite.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
