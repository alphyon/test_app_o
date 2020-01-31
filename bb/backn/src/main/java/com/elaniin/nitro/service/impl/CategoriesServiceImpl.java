package com.elaniin.nitro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elaniin.nitro.dto.CategoriesDTO;
import com.elaniin.nitro.entity.Categories;
import com.elaniin.nitro.repository.CategoriesRepository;
import com.elaniin.nitro.service.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CategoriesDTO> categoriesList() {
		List<CategoriesDTO> categoriesList = new ArrayList<>();
		for (Categories categories : categoriesRepository.findAll()) {
			categoriesList.add(convertToDTO(categories));
		}
		return categoriesList;
	}

	@Override
	public Categories storeCategories(CategoriesDTO dto) {
		final Categories categories = new Categories(dto.getName());
		return categoriesRepository.save(categories);
	}

	@Override
	public Categories updateCategories(CategoriesDTO dto) {
		Categories categories = categoriesRepository.findById(dto.getId()).get();
		if (categories != null) {
			categories.setCategoryName(dto.getName());
			categoriesRepository.save(categories);
		}
		return categories;
	}

	private CategoriesDTO convertToDTO(Categories categories) {
		CategoriesDTO cateDto = modelMapper.map(categories, CategoriesDTO.class);
		return cateDto;
	}

	@Override
	public boolean deleteCategory(Integer id) {
		boolean existCategory = categoriesRepository.existsById(id.longValue());
		if (existCategory) {
			categoriesRepository.deleteById(id.longValue());
		}
		return existCategory;
	}
}