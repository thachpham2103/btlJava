package org.example.controller;

import org.example.utils.Validator;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.example.domain.model.Role;
import org.example.domain.model.Status;
import org.example.domain.model.UserModel;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;
import org.example.utils.PasswordEncoder;
import org.example.view.component.UserComponent.UserPanel;


public class UserController {
 private final UserService userService;
    private JComponent viewComponent;
    private JTable userTable;

    public UserController() {
        this.userService = new UserServiceImpl();
    }

    public void setUserTable(JTable userTable) {
        this.userTable = userTable;
    }

    public void setView(UserPanel panel) {
        this.viewComponent = panel;
        this.userTable = panel.getTblUsers();
    }

    public void loadUsers(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        List<UserModel> users = userService.getAllUsers();
        for (UserModel user : users) {
            model.addRow(new Object[]{
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getStatus(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            });
        }
    }

    public void searchUsers(String searchTerm, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (searchTerm == null || searchTerm.isEmpty()) {
            loadUsers(table);
            return;
        }

        List<UserModel> users = userService.getAllUsers();
        List<UserModel> filtered = users.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();

        for (UserModel user : filtered) {
            model.addRow(new Object[]{
                    user.getId(),
                    user.getUsername(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getAddress(),
                    user.getStatus(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            });
        }
    }

    public void addUser(String username, String password, String fullName, String email, String phone, String address, Role role, Status status) {
        if (userService.existsByUsername(username)) {
            JOptionPane.showMessageDialog(viewComponent, "Username đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (userService.existsByEmail(email)) {
            JOptionPane.showMessageDialog(viewComponent, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(viewComponent, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(viewComponent, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hashedPassword = PasswordEncoder.encode(password);
        UserModel user = UserModel.builder()
                .username(username)
                .password(hashedPassword)
                .fullName(fullName)
                .email(email)
                .phoneNumber(phone)
                .address(address)
                .role(role)
                .status(status)
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        boolean success = userService.createUser(user);
        if (success) {
            JOptionPane.showMessageDialog(viewComponent, "Thêm người dùng thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers(userTable);
        } else {
            JOptionPane.showMessageDialog(viewComponent, "Thêm người dùng thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateUser(Long id, String username, String password, String fullName, String email, String phone, String address, Role role, Status status) {
        UserModel existing = userService.getUserById(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(viewComponent, "Người dùng không tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.equals(existing.getEmail()) && userService.existsByEmail(email)) {
            JOptionPane.showMessageDialog(viewComponent, "Email đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(viewComponent, "Email không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!Validator.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(viewComponent, "Số điện thoại không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        existing.setUsername(username);
        existing.setPassword(PasswordEncoder.encode(password));
        existing.setFullName(fullName);
        existing.setEmail(email);
        existing.setPhoneNumber(phone);
        existing.setAddress(address);
        existing.setRole(role);
        existing.setStatus(status);
        existing.setUpdatedAt(LocalDate.now());

        boolean success = userService.updateUser(id, existing);
        if (success) {
            JOptionPane.showMessageDialog(viewComponent, "Cập nhật thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUsers(userTable);
        } else {
            JOptionPane.showMessageDialog(viewComponent, "Cập nhật thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteUser(Long id) {
        int confirm = JOptionPane.showConfirmDialog(viewComponent, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = userService.deleteUser(id);
            if (success) {
                JOptionPane.showMessageDialog(viewComponent, "Xóa thành công!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadUsers(userTable);
            } else {
                JOptionPane.showMessageDialog(viewComponent, "Xóa thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public UserModel getUserById(Long id) {
        return userService.getUserById(id);
    }
    
    public boolean existsByUsername(String username) {
         return userService.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
         return userService.existsByEmail(email);
    }
}
