package com.elaniin.nitro.service;

import java.util.List;

import com.elaniin.nitro.dto.CategoriesDTO;
import com.elaniin.nitro.entity.Categories;

public interface CategoriesService {

	List<CategoriesDTO> categoriesList();

	Categories storeCategories(CategoriesDTO categories);

	Categories updateCategories(CategoriesDTO categories);
	
	boolean deleteCategory(Integer id);

}
