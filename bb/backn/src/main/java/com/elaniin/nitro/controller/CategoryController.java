package com.elaniin.nitro.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.elaniin.nitro.dto.CategoriesDTO;
import com.elaniin.nitro.entity.Categories;
import com.elaniin.nitro.exception.ResourceNotFoundException;
import com.elaniin.nitro.service.CategoriesService;
import com.elaniin.nitro.util.ConstantMessages;
import com.elaniin.nitro.util.GenericBase;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "CRUD CONTROLLERS", description = "CRUD for manage all collectors")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController extends GenericBase {

	public static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoriesService iCategoriesService;

	@ApiOperation(value = "List all Channels availables for an Collector")
	@GetMapping("categorylist")
	public ResponseEntity<Object> categoriesList(WebRequest request) throws JsonProcessingException {
		List<CategoriesDTO> categoriesDTOs = iCategoriesService.categoriesList();
		if (categoriesDTOs.isEmpty()) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCESS_LIST, request, categoriesDTOs);
	}

	@ApiOperation(value = "Function for store  collectors")
	@PostMapping("store")
	public ResponseEntity<Object> storeCategories(@RequestBody CategoriesDTO categoriesDTO, WebRequest request)
			throws JsonProcessingException {
		Categories categories;
		try {
			categories = iCategoriesService.storeCategories(categoriesDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.SUCCESS_MESSAGE, request, categories);
	}

	@ApiOperation(value = "Function to Update a Collector")
	@PutMapping("update/{id}")
	public ResponseEntity<Object> updateCategories(@RequestBody CategoriesDTO categoriesDTO,
			@ApiParam(value = "Category ID from which category will be updated from databas", required = true, example = "1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		Categories categories;
		try {
			categories = iCategoriesService.updateCategories(categoriesDTO);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new ResourceNotFoundException(ConstantMessages.SERVER_ERROR);
		}
		return genericCustomMessageResponse(ConstantMessages.UPDATE_MESSAGE, request, categories);
	}

	@ApiOperation(value = "Function to delete a Collector")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> deleteCategory(
			@ApiParam(value = "Category ID from which category will be deleted from database", required = true, example = "1") @PathVariable Integer id,
			WebRequest request) throws JsonProcessingException {
		boolean deleted = false;
		deleted = iCategoriesService.deleteCategory(id.intValue());
		if (!deleted) {
			throw new ResourceNotFoundException(ConstantMessages.EMPTY_DATA);
		}
		return genericCustomMessageResponse(ConstantMessages.DELETE_MESSAGE, request, deleted);
	}

}
