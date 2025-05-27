package org.example.controller;

import org.example.domain.model.CategoryModel;
import org.example.domain.model.ProductModel;
import org.example.service.CategoryService;
import org.example.service.ProductService;
import org.example.service.impl.CategoryServiceImpl;
import org.example.service.impl.ProductServiceImpl;
import org.example.view.component.ProductComponent.ProductFormDialog;
import org.example.view.component.ProductComponent.ProductPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;


public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController() {
        this.productService = new ProductServiceImpl();
        this.categoryService = new CategoryServiceImpl();
    }

    public boolean saveProduct(ProductModel productModel) {
        if (productModel.getId() == null) {
            return productService.createProduct(productModel);
        } else {
            return productService.updateProduct(productModel.getId(), productModel);
        }
    }

    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    public ProductModel getProductById(Long id) {
        return productService.getProductById(id);
    }

    public boolean deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }

    public List<CategoryModel> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public void loadProductsToTable(ProductPanel productPanel) {
        JTable table = productPanel.getTblProducts();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<ProductModel> products = getAllProducts();
        for (ProductModel product : products) {
            model.addRow(new Object[] {
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getRemainingQuantity(),
                    product.getSoldQuantity(),
                    product.getCategoryModel() != null ? product.getCategoryModel().getName() : "",
                    product.getCreatedAt()
            });
        }
    }

    public void handleAddProduct(ProductPanel productPanel) {
        ProductFormDialog dialog = new ProductFormDialog(null, true, this);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        loadProductsToTable(productPanel);
    }

    public void handleEditProduct(ProductPanel productPanel, Long productId) {
        ProductModel product = getProductById(productId);
        if (product != null) {
            ProductFormDialog dialog = new ProductFormDialog(null, true, this, product);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            loadProductsToTable(productPanel);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteProduct(ProductPanel productPanel, Long productId) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn chắc chắn muốn xóa sản phẩm này?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (deleteProduct(productId)) {
                JOptionPane.showMessageDialog(null, "Xóa sản phẩm thành công");
                loadProductsToTable(productPanel);
            } else {
                JOptionPane.showMessageDialog(null, "Xóa sản phẩm thất bại", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void searchProducts(String searchTerm, ProductPanel productPanel) {
        JTable table = productPanel.getTblProducts();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (searchTerm == null || searchTerm.isEmpty()) {
            loadProductsToTable(productPanel);
            return;
        }

        List<ProductModel> products = productService.getAllProducts();
        List<ProductModel> filteredProducts = products.stream()
                .filter(product -> product.getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                        (product.getCategoryModel() != null &&
                                product.getCategoryModel().getName().toLowerCase().contains(searchTerm.toLowerCase())))
                .toList();

        for (ProductModel product : filteredProducts) {
            model.addRow(new Object[] {
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getRemainingQuantity(),
                    product.getSoldQuantity(),
                    product.getCategoryModel() != null ? product.getCategoryModel().getName() : "",
                    product.getCreatedAt()
            });
        }
    }
}