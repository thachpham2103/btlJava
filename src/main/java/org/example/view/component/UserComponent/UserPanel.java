/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.view.component.UserComponent;

import org.example.controller.UserController;
import org.example.domain.model.UserModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 *
 * @author ADMIN
 */
public class UserPanel extends JPanel {

    private final JTable tblUsers;
    private final JButton btnAdd, btnEdit, btnDelete, btnRefresh;
    private final JTextField txtSearch;
    private final JButton btnSearch;
    private final UserController controller;

    public UserPanel(JFrame parentFrame) {
        controller = new UserController();
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout(10, 0));
        topPanel.setBackground(Color.WHITE);

        txtSearch = new JTextField();
        btnSearch = new JButton("Search");
        topPanel.add(txtSearch, BorderLayout.CENTER);
        topPanel.add(btnSearch, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        // Table
        tblUsers = new JTable();
      tblUsers.setModel(new DefaultTableModel(
        new Object[][]{},
        new String[]{"ID", "Username", "Full Name", "Email", "Phone", "Address", "Status", "Created At", "Updated At"}
));


        JScrollPane scrollPane = new JScrollPane(tblUsers);

        // Add to panel
        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        controller.setUserTable(tblUsers);
        controller.loadUsers(tblUsers);

        // Event listeners
        initListeners();
    }

    private void initListeners() {
        btnAdd.addActionListener(e -> {
            new UserFormDialog(null, controller, tblUsers, null).setVisible(true);
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = tblUsers.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to edit", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Long userId = (Long) tblUsers.getValueAt(selectedRow, 0);
            UserModel user = controller.getUserById(userId);
            if (user != null) {
                new UserFormDialog(null, controller, tblUsers, user).setVisible(true);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = tblUsers.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a user to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Long userId = (Long) tblUsers.getValueAt(selectedRow, 0);
            controller.deleteUser(userId);
        });

        btnRefresh.addActionListener(e -> controller.loadUsers(tblUsers));

        btnSearch.addActionListener(e -> {
            String searchTerm = txtSearch.getText().trim();
            controller.searchUsers(searchTerm, tblUsers);
        });
    }

    public JTable getTblUsers() {
        return tblUsers;
    }
}
