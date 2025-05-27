/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.view.component.ImportProductCompoment;

import org.example.controller.ImportProductController;
import org.example.domain.model.ImportProductModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Getter;
import net.miginfocom.swing.MigLayout;
/**
 *
 * @author ADMIN
 */
public class ImportProductPanel extends JPanel {
  
  private final JTable tblImportProducts;
    private final JTextField txtSearch;
    private final JButton btnAdd, btnEdit, btnDelete, btnSearch;
    private final ImportProductController controller;

    public ImportProductPanel() {
        this.controller = new ImportProductController();

        setLayout(new MigLayout("fill", "[grow][]", "[][grow][]"));

        txtSearch = new JTextField(20);
        btnSearch = new JButton("Tìm kiếm");
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Tìm kiếm:"));
        topPanel.add(txtSearch);
        topPanel.add(btnSearch);
        topPanel.add(btnAdd);
        topPanel.add(btnEdit);
        topPanel.add(btnDelete);

        add(topPanel, "span, wrap");

        tblImportProducts = new JTable(new DefaultTableModel(new Object[]{
                "ID", "Ngày nhập", "Người nhập", "Nhà cung cấp", "Số lượng SP"}, 0));
        JScrollPane scrollPane = new JScrollPane(tblImportProducts);
        add(scrollPane, "span, grow, wrap");

        controller.loadImportProductsToTable(this);

        btnAdd.addActionListener(e -> controller.handleAddImportProduct(this));
        btnEdit.addActionListener(this::handleEditAction);
        btnDelete.addActionListener(this::handleDeleteAction);
        btnSearch.addActionListener(e ->
                controller.searchImportProducts(txtSearch.getText().trim(), this));
    }

    private void handleEditAction(ActionEvent e) {
        int selectedRow = tblImportProducts.getSelectedRow();
        if (selectedRow >= 0) {
            Long id = (Long) tblImportProducts.getValueAt(selectedRow, 0);
            controller.handleEditImportProduct(this, id);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để sửa");
        }
    }

    private void handleDeleteAction(ActionEvent e) {
        int selectedRow = tblImportProducts.getSelectedRow();
        if (selectedRow >= 0) {
            Long id = (Long) tblImportProducts.getValueAt(selectedRow, 0);
            controller.handleDeleteImportProduct(this, id);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa");
        }
    }
    public JTable getTblImportProducts() {
    return tblImportProducts;
}
}
