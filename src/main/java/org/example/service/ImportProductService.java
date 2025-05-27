/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.example.service;

import java.util.List;
import org.example.domain.model.ImportProductModel;
import org.example.domain.model.ProductModel;
import org.example.domain.model.SupplierModel;
import org.example.domain.model.UserModel;

/**
 *
 * @author ADMIN
 */
public interface ImportProductService {
    
     boolean createImportProduct(ImportProductModel importProductModel);
    List<ImportProductModel> getAllImportProducts();
    ImportProductModel getImportProductById(Long id);
    boolean updateImportProduct(Long id, ImportProductModel importProductModel);
    boolean deleteImportProduct(Long id);
    List<UserModel> getAllUsers();
//    List<SupplierModel> getAllSuppliers();
    List<ProductModel> getAllProducts();
}
