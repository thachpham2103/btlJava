package org.example.service;

import org.example.domain.model.CategoryModel;

import java.util.List;

public interface CategoryService {

    boolean createCategory(CategoryModel categoryModel);
    List<CategoryModel> getAllCategories();
    CategoryModel getCategoryById(Long id);
    boolean updateCategory(Long id, CategoryModel categoryModel);
    boolean deleteCategory(Long id);

}
