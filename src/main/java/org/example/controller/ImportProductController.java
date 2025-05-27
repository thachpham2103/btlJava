/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.controller;

//import static com.microsoft.schemas.office.excel.STCF.Enum.table;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.example.domain.model.ImportProductModel;
import org.example.domain.model.ProductModel;
import org.example.domain.model.SupplierModel;
import org.example.domain.model.UserModel;
import org.example.service.ImportProductService;
import org.example.service.impl.ImportProductServiceImpl;
import org.example.view.component.ImportProductCompoment.ImportProductFormDiaLog;
import org.example.view.component.ImportProductCompoment.ImportProductPanel;

/**
 *
 * @author ADMIN
 */
public class ImportProductController {
   private final ImportProductService importProductService;

    public ImportProductController() {
        this.importProductService = new ImportProductServiceImpl();
    }

    public boolean saveImportProduct(ImportProductModel importProductModel) {
        if (importProductModel.getId() == null) {
            return importProductService.createImportProduct(importProductModel);
        } else {
            return importProductService.updateImportProduct(importProductModel.getId(), importProductModel);
        }
    }
    public List<ImportProductModel> getAllImportProducts() {
        return importProductService.getAllImportProducts();
    }

    public ImportProductModel getImportProductById(Long id) {
        return importProductService.getImportProductById(id);
    }

    public boolean deleteImportProduct(Long id) {
        return importProductService.deleteImportProduct(id);
    }

    public void loadImportProductsToTable(ImportProductPanel panel) {
        JTable table = panel.getTblImportProducts();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<ImportProductModel> imports = getAllImportProducts();
        for (ImportProductModel imp : imports) {
            model.addRow(new Object[]{
                    imp.getId(),
                    imp.getImportDate(),
                    imp.getUserModel() != null ? imp.getUserModel().getUsername() : "",
                    imp.getSupplierModel() != null ? imp.getSupplierModel().getName() : "",
                    imp.getImportDetails() != null ? imp.getImportDetails().size() : 0
            });
        }
    }

    public void handleAddImportProduct(ImportProductPanel panel) {
        ImportProductFormDiaLog dialog = new ImportProductFormDiaLog(null, true, this);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        loadImportProductsToTable(panel);
    }

    public void handleEditImportProduct(ImportProductPanel panel, Long importId) {
        ImportProductModel imp = getImportProductById(importId);
        if (imp != null) {
            ImportProductFormDiaLog dialog = new ImportProductFormDiaLog(null, true, this, imp);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
            loadImportProductsToTable(panel);
        } else {
            JOptionPane.showMessageDialog(null, "Không tìm thấy phiếu nhập", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleDeleteImportProduct(ImportProductPanel panel, Long importId) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn chắc chắn muốn xóa phiếu nhập này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (deleteImportProduct(importId)) {
                JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thành công");
                loadImportProductsToTable(panel);
            } else {
                JOptionPane.showMessageDialog(null, "Xóa phiếu nhập thất bại", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void searchImportProducts(String searchTerm, ImportProductPanel panel) {
        JTable table = panel.getTblImportProducts();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            loadImportProductsToTable(panel);
            return;
        }

        List<ImportProductModel> allImports = getAllImportProducts();
        List<ImportProductModel> filtered = allImports.stream()
                .filter(imp ->
                        (imp.getSupplierModel() != null &&
                                imp.getSupplierModel().getName().toLowerCase().contains(searchTerm.toLowerCase())) ||
                        (imp.getUserModel() != null &&
                                imp.getUserModel().getUsername().toLowerCase().contains(searchTerm.toLowerCase()))
                )
                .toList();

        for (ImportProductModel imp : filtered) {
            model.addRow(new Object[]{
                    imp.getId(),
                    imp.getImportDate(),
                    imp.getUserModel() != null ? imp.getUserModel().getUsername() : "",
                    imp.getSupplierModel() != null ? imp.getSupplierModel().getName() : "",
                    imp.getImportDetails() != null ? imp.getImportDetails().size() : 0
            });
        }
    }

    // ✅ Các phương thức load dữ liệu cho Form Dialog
    public List<UserModel> getAllUsers() {
        return importProductService.getAllUsers();
    }
    
    public List<ProductModel> getAllProducts() {
        return importProductService.getAllProducts();
    }
}
