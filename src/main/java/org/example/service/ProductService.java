package org.example.service;

import org.example.domain.model.ProductModel;

import java.util.List;

public interface ProductService {

    boolean createProduct(ProductModel productModel);
    List<ProductModel> getAllProducts();
    ProductModel getProductById(Long id);
    boolean updateProduct(Long id, ProductModel productModel);
    boolean deleteProduct(Long id);
}
