package org.example.view.component.ProductComponent;

import lombok.Getter;
import org.example.controller.ProductController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductPanel extends JPanel {
    private final ProductController controller;
    private final JFrame parentFrame;

    @Getter
    private JTable tblProducts;
    private JTextField txtSearch;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;

    public ProductPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        controller = new ProductController();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(245, 245, 247));

        initComponents();
        initListeners();
        controller.loadProductsToTable(this);
    }

    private void initComponents() {
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 247));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Product Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(new Color(245, 245, 247));

        txtSearch = new JTextField(15);
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204), 1, true),
                BorderFactory.createEmptyBorder(7, 10, 7, 10)));

        btnSearch = new JButton("Search");
        styleButton(btnSearch, new Color(92, 184, 92));
        btnSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(92, 184, 92).darker(), 1),
                BorderFactory.createEmptyBorder(3, 10, 3, 10)));

        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        headerPanel.add(searchPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        String[] columns = { "ID", "Name", "Price", "Remaining Qty", "Sold Qty", "Category", "Created At" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProducts = new JTable(model);
        tblProducts.setRowHeight(30);
        tblProducts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblProducts.setSelectionBackground(new Color(44, 62, 80));
        tblProducts.setGridColor(new Color(240, 240, 240));
        tblProducts.setShowVerticalLines(false);

        JTableHeader header = tblProducts.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(51, 51, 51));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(204, 204, 204)));
        header.setPreferredSize(new Dimension(100, 35));

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tblProducts.getColumnCount(); i++) {
            tblProducts.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tblProducts);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(245, 245, 247));

        btnAdd = new JButton("Add");
        styleButton(btnAdd, new Color(66, 139, 202));

        btnUpdate = new JButton("Update");
        styleButton(btnUpdate, new Color(91, 192, 222));

        btnDelete = new JButton("Delete");
        styleButton(btnDelete, new Color(217, 83, 79));

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        return buttonPanel;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> {
            controller.handleAddProduct(this);
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = tblProducts.getSelectedRow();
            if (selectedRow >= 0) {
                Long productId = (Long) tblProducts.getValueAt(selectedRow, 0);
                controller.handleEditProduct(this, productId);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn sản phẩm muốn cập nhật", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = tblProducts.getSelectedRow();
            if (selectedRow >= 0) {
                Long productId = (Long) tblProducts.getValueAt(selectedRow, 0);
                controller.handleDeleteProduct(this, productId);
            } else {
                JOptionPane.showMessageDialog(this, "Chọn sản phẩm muốn xóa", "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            String searchTerm = txtSearch.getText().trim();
            searchProducts(searchTerm);
        });

        tblProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = tblProducts.getSelectedRow();
                    if (selectedRow >= 0) {
                        Long productId = (Long) tblProducts.getValueAt(selectedRow, 0);
                        controller.handleEditProduct(ProductPanel.this, productId);
                    }
                }
            }
        });
    }

    private void searchProducts(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) tblProducts.getModel();
        model.setRowCount(0);

        if (searchTerm.isEmpty()) {
            controller.loadProductsToTable(this);
            return;
        }

        controller.searchProducts(searchTerm, this);
    }
}
