package org.example.view.component.CategoryComponent;

import net.miginfocom.swing.MigLayout;
import org.example.controller.CategoryController;
import org.example.domain.model.CategoryModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CategoryFormDialog extends JDialog {

    private final CategoryController controller;
    private Long categoryId;
    private final JTextField txtCategoryId;
    private final JTextField txtCategoryName;
    private final JButton btnSave;
    private final JButton btnCancel;
    private final JTable parentTable;
    private final boolean isEdit;

    public CategoryFormDialog(JFrame parent, CategoryController controller, JTable parentTable) {
        this(parent, controller, parentTable, null);
    }

    public CategoryFormDialog(JFrame parent, CategoryController controller, JTable parentTable,
            CategoryModel category) {
        super(parent, true);
        this.controller = controller;
        this.parentTable = parentTable;
        this.isEdit = category != null;

        controller.setCategoryTable(parentTable);

        txtCategoryId = new JTextField(20);
        txtCategoryName = new JTextField(20);
        btnSave = new JButton(isEdit ? "Update" : "Save");
        btnCancel = new JButton("Cancel");

        if (isEdit && category != null) {
            categoryId = category.getId();
            txtCategoryId.setText(String.valueOf(category.getId()));
            txtCategoryName.setText(category.getName());
        }

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setTitle(isEdit ? "Edit Category" : "Add New Category");
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel(isEdit ? "Edit Category" : "Add New Category");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new MigLayout("fillx, insets 0", "[right]10[grow,fill]", "[]15[]"));
        formPanel.setBackground(Color.WHITE);

        if (isEdit) {
            JLabel lblCategoryId = new JLabel("Category ID:");
            lblCategoryId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            formPanel.add(lblCategoryId, "");

            txtCategoryId.setEditable(false);
            txtCategoryId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            txtCategoryId.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(204, 204, 204), 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)));
            formPanel.add(txtCategoryId, "wrap");
        }

        JLabel lblCategoryName = new JLabel("Category Name:");
        lblCategoryName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(lblCategoryName, "");

        txtCategoryName.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCategoryName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        formPanel.add(txtCategoryName, "wrap");

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        styleButton(btnSave, new Color(66, 139, 202));
        styleButton(btnCancel, new Color(153, 153, 153));

        buttonPanel.add(btnCancel);
        buttonPanel.add(btnSave);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void initListeners() {
        btnSave.addActionListener(actionEvent -> {
            String name = txtCategoryName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a category name",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (isEdit) {
                controller.updateCategory(categoryId, name);
            } else {
                controller.addCategory(name);
            }
            SwingUtilities.invokeLater(() -> {
                controller.loadCategories(parentTable);
            });
            dispose();
        });

        btnCancel.addActionListener(actionEvent -> dispose());
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}