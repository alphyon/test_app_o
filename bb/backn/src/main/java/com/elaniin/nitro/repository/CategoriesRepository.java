package com.elaniin.nitro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elaniin.nitro.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}
