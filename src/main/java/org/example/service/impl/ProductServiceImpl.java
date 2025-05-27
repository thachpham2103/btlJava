package org.example.service.impl;


import org.example.domain.model.ProductModel;
import org.example.repository.ProductDAO;
import org.example.service.ProductService;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO = new ProductDAO();

    @Override
    public boolean createProduct(ProductModel productModel) {
        try{
            productDAO.createProduct(productModel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ProductModel> getAllProducts() {
        List<ProductModel> productModels = new ArrayList<>();
        try {
            productModels = productDAO.getAllProducts();
            return productModels;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return productModels;
    }

    @Override
    public ProductModel getProductById(Long id) {
        ProductModel productModel = new ProductModel();
        try {
            productModel = productDAO.getProductById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return productModel;
    }

    @Override
    public boolean updateProduct(Long id, ProductModel productModel) {
        try {
            productDAO.updateProduct(id, productModel);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            productDAO.deleteProduct(id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
