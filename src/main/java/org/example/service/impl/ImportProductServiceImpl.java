/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.example.domain.model.ImportProductModel;
import org.example.domain.model.ProductModel;
import org.example.domain.model.SupplierModel;
import org.example.domain.model.UserModel;
import org.example.repository.ImportProductDAO;
import org.example.repository.ProductDAO;
import org.example.repository.SupplierDAO;
import org.example.repository.UserDAO;
import org.example.service.ImportProductService;

/**
 *
 * @author ADMIN
 */
public class ImportProductServiceImpl implements ImportProductService{

 
    private final ImportProductDAO importProductDAO = new ImportProductDAO();
    private final UserDAO userDAO = new UserDAO();
    private final SupplierDAO supplierDAO = new SupplierDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    public boolean createImportProduct(ImportProductModel importProductModel) {
        try {
            importProductDAO.create(importProductModel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ImportProductModel> getAllImportProducts() {
        try {
            return importProductDAO.getAllImportProducts();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ImportProductModel getImportProductById(Long id) {
        try {
            return importProductDAO.getImportPoroductById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateImportProduct(Long id, ImportProductModel importProductModel) {
        try {
            importProductDAO.update(id, importProductModel);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteImportProduct(Long id) {
        try {
            importProductDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Triển khai các phương thức mới
    @Override
    public List<UserModel> getAllUsers() {
        try {
            return userDAO.getAllUser();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    @Override
//    public List<SupplierModel> getAllSuppliers() {
//        try {
//            return supplierDAO.getAllSuppliers();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }

    @Override
    public List<ProductModel> getAllProducts() {
        try {
            return productDAO.getAllProducts();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    @Override
//    public boolean saveImportProductWithDetail(ImportProductModel model, ProductModel product, int quantity, double price) {
//        try {
//            return importProductDAO.saveImportProductWithDetail(model, product, quantity, price);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
