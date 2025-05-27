/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.view.component.UserComponent;

/**
 *
 * @author ADMIN
 */
import net.miginfocom.swing.MigLayout;
import org.example.controller.UserController;
import org.example.domain.model.Role;
import org.example.domain.model.Status;
import org.example.domain.model.UserModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;
import org.example.utils.PasswordEncoder;

public class UserFormDialog extends JDialog {

   private final UserController controller;
    private Long userId;
    private final JTextField txtUsername;
    private final JPasswordField txtPassword;
    private final JTextField txtFullName;
    private final JTextField txtEmail;
    private final JTextField txtPhoneNumber;
    private final JTextField txtAddress;
    private final JComboBox<Role> comboRole;
    private final JComboBox<Status> comboStatus;
    private final JButton btnSave;
    private final JButton btnCancel;
    private final JTable parentTable;
    private final boolean isEdit;

    public UserFormDialog(JFrame parent, UserController controller, JTable parentTable, UserModel user) {
        super(parent, true);
        this.controller = controller;
        this.parentTable = parentTable;
        this.isEdit = user != null;

        controller.setUserTable(parentTable);

        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtFullName = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPhoneNumber = new JTextField(20);
        txtAddress = new JTextField(20);
        comboRole = new JComboBox<>(Role.values());
        comboStatus = new JComboBox<>(Status.values());

        btnSave = new JButton(isEdit ? "Update" : "Save");
        btnCancel = new JButton("Cancel");

        if (isEdit) {
            userId = user.getId();
            txtUsername.setText(user.getUsername());
            txtPassword.setText(""); // Không hiển thị mật khẩu đã mã hóa
            txtFullName.setText(user.getFullName());
            txtEmail.setText(user.getEmail());
            txtPhoneNumber.setText(user.getPhoneNumber());
            txtAddress.setText(user.getAddress());
            comboRole.setSelectedItem(user.getRole());
            comboStatus.setSelectedItem(user.getStatus());
        }

        initComponents();
        initListeners();
    }

    private void initComponents() {
        setTitle(isEdit ? "Edit User" : "Add New User");
        setSize(500, 500);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(isEdit ? "Edit User" : "Add New User");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(51, 51, 51));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new MigLayout("fillx, insets 0", "[right]10[grow,fill]", ""));
        formPanel.setBackground(Color.WHITE);

        addFormField(formPanel, "Username:", txtUsername);
        addFormField(formPanel, "Password:", txtPassword);
        addFormField(formPanel, "Full Name:", txtFullName);
        addFormField(formPanel, "Email:", txtEmail);
        addFormField(formPanel, "Phone Number:", txtPhoneNumber);
        addFormField(formPanel, "Address:", txtAddress);
        addFormField(formPanel, "Role:", comboRole);
        addFormField(formPanel, "Status:", comboStatus);

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
        btnSave.addActionListener(e -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhoneNumber.getText().trim();
            String address = txtAddress.getText().trim();
            Role role = (Role) comboRole.getSelectedItem();
            Status status = (Status) comboStatus.getSelectedItem();

            // Validate empty
            if (username.isEmpty() || (!isEdit && password.isEmpty()) || fullName.isEmpty() ||
                email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate email format
            if (!Pattern.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$", email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate phone format
            if (!Pattern.matches("^(0[1-9][0-9]{8,9})$", phone)) {
                JOptionPane.showMessageDialog(this, "Invalid phone number format", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check trùng username / email nếu tạo mới hoặc sửa username/email
            if (!isEdit || !username.equals(controller.getUserById(userId).getUsername())) {
                if (controller.existsByUsername(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (!isEdit || !email.equals(controller.getUserById(userId).getEmail())) {
                if (controller.existsByEmail(email)) {
                    JOptionPane.showMessageDialog(this, "Email already exists", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Mã hóa mật khẩu nếu tạo mới hoặc nhập lại
            String encodedPassword = isEdit && password.isEmpty()
                    ? controller.getUserById(userId).getPassword()
                    : PasswordEncoder.encode(password);

            if (isEdit) {
                controller.updateUser(userId, username, encodedPassword, fullName, email, phone, address, role, status);
            } else {
                controller.addUser(username, encodedPassword, fullName, email, phone, address, role, status);
            }

            SwingUtilities.invokeLater(() -> controller.loadUsers(parentTable));
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());
    }

    private void addFormField(JPanel panel, String label, JComponent input) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lbl);
        input.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        input.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 204, 204), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        panel.add(input, "wrap");
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