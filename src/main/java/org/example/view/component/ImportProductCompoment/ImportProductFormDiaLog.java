    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package org.example.view.component.ImportProductCompoment;


    import org.example.controller.ImportProductController;
    import org.example.domain.model.ImportProductModel;
    import org.example.domain.model.ProductModel;

    import javax.swing.*;
    import java.awt.*;
    import java.time.LocalDate;
    import net.miginfocom.swing.MigLayout;
    import org.example.domain.model.SupplierModel;
    import org.example.domain.model.UserModel;

    /**
     *
     * @author ADMIN
     */
    public class ImportProductFormDiaLog extends JDialog {

         private final JTextField txtDate;
    private final JComboBox<UserModel> cboUser;
    private final JComboBox<SupplierModel> cboSupplier;
    private final JComboBox<ProductModel> cboProduct;
    private final JTextField txtQuantity;
    private final JTextField txtPrice;
    private final JButton btnSave;

    private final ImportProductController controller;
    private final ImportProductModel existing;

    public ImportProductFormDiaLog(Frame parent, boolean modal, ImportProductController controller) {
        this(parent, modal, controller, null);
    }

    public ImportProductFormDiaLog(Frame parent, boolean modal, ImportProductController controller, ImportProductModel model) {
        super(parent, modal);
        this.controller = controller;
        this.existing = model;

        setTitle(model == null ? "Thêm phiếu nhập" : "Sửa phiếu nhập");
        setLayout(new MigLayout("wrap 2", "[][grow]", "[]15[]15[]15[]15[]15[]15[]"));
        setSize(450, 350);
        setLocationRelativeTo(null);

        txtDate = new JTextField(LocalDate.now().toString(), 20);
        txtDate.setEditable(false);

        cboUser = new JComboBox<>();
        cboSupplier = new JComboBox<>();
        cboProduct = new JComboBox<>();
        txtQuantity = new JTextField(10);
        txtPrice = new JTextField(10);
        btnSave = new JButton("Lưu");

        // Load dữ liệu combobox từ controller
//        List<UserModel> users = controller.getAllUsers();
//        for (UserModel user : users) {
//            cboUser.addItem(user);
//        }
//
//        List<SupplierModel> suppliers = controller.getAllSuppliers();
//        for (SupplierModel supplier : suppliers) {
//            cboSupplier.addItem(supplier);
//        }
//
//        List<ProductModel> products = controller.getAllProducts();
//        for (ProductModel product : products) {
//            cboProduct.addItem(product);
//        }

        add(new JLabel("Ngày nhập:"));
        add(txtDate);
        add(new JLabel("Người nhập:"));
        add(cboUser);
        add(new JLabel("Nhà cung cấp:"));
        add(cboSupplier);
        add(new JLabel("Sản phẩm:"));
        add(cboProduct);
        add(new JLabel("Số lượng:"));
        add(txtQuantity);
        add(new JLabel("Giá nhập:"));
        add(txtPrice);
        add(new JLabel(""));
        add(btnSave, "right");

        // Nếu là sửa
        if (existing != null) {
            txtDate.setText(existing.getImportDate().toString());
            cboUser.setSelectedItem(existing.getUserModel());
            cboSupplier.setSelectedItem(existing.getSupplierModel());
            // Bạn có thể cập nhật thêm chi tiết sản phẩm nếu đã có dữ liệu ImportDetail
        }

        btnSave.addActionListener(e -> {
    try {
        int quantity = Integer.parseInt(txtQuantity.getText().trim());
        double price = Double.parseDouble(txtPrice.getText().trim());

        if (quantity <= 0 || price <= 0) {
            JOptionPane.showMessageDialog(this, "Số lượng và giá nhập phải lớn hơn 0");
            return;
        }

        ImportProductModel imp = new ImportProductModel();
        imp.setId(existing != null ? existing.getId() : null);
        imp.setImportDate(LocalDate.now());
        imp.setUserModel((UserModel) cboUser.getSelectedItem());
        imp.setSupplierModel((SupplierModel) cboSupplier.getSelectedItem());

        ProductModel selectedProduct = (ProductModel) cboProduct.getSelectedItem();

        // GỌI controller để lưu
//        boolean success = controller.saveImportProductWithDetail(imp, selectedProduct, quantity, price);
//        if (success) {
//            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thành công!");
//            dispose(); // Đóng form sau khi lưu
//        } else {
//            JOptionPane.showMessageDialog(this, "Lưu phiếu nhập thất bại!");
//        }

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng số cho Số lượng và Giá nhập.");
    }
});

    }
    }
