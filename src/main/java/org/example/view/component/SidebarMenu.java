package org.example.view.component;

import org.example.view.MainDashboard;
import org.example.view.component.CategoryComponent.CategoryPanel;
import org.example.view.component.ProductComponent.ProductPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import org.example.view.component.ImportProductCompoment.ImportProductPanel;
import org.example.view.component.UserComponent.UserPanel;

public class SidebarMenu extends JPanel {

    private JLabel lblSelectedMenu;
    private final JFrame parentFrame;
    private final Map<String, JPanel> menuPanels = new HashMap<>();

    public SidebarMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(44, 62, 80));
        setPreferredSize(new Dimension(230, parentFrame.getHeight()));
        setBorder(new EmptyBorder(20, 0, 0, 0));

        String[] menuItems = {
                "DASHBOARD",
                "CATEGORIES",
                "PRODUCTS",
                "SUPPLIERS",
                "CUSTOMERS",
                "IMPORT PRODUCTS",
                "EXPORT PRODUCTS",
                "USERS"
        };

        for (String menuItem : menuItems) {
            JPanel menuPanel = createMenuItemPanel(menuItem);
            menuPanels.put(menuItem, menuPanel);
            add(menuPanel);
        }

        add(Box.createVerticalGlue());
        JPanel logoutPanel = createMenuItemPanel("Logout");
        add(logoutPanel);
        add(Box.createVerticalStrut(20));
    }

    private JPanel createCategoryPanel() {
        return new CategoryPanel(parentFrame);
    }

    private JPanel createProductPanel() {
        return new ProductPanel(parentFrame);
    }

    
    private JPanel createUserPanel(){
        return new UserPanel(parentFrame);
    }
    
    private JPanel createPlaceholderPanel(){
        return new ImportProductPanel();
    }
    private JPanel createMenuItemPanel(String menuText) {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80));
        menuPanel.setBorder(new EmptyBorder(12, 20, 12, 20));
        menuPanel.setMaximumSize(new Dimension(250, 50));
        menuPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMenu = new JLabel(menuText);
        lblMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMenu.setForeground(Color.WHITE);
        menuPanel.add(lblMenu);

        menuPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuPanel.setBackground(new Color(52, 73, 94));
                parentFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (lblSelectedMenu != lblMenu) {
                    menuPanel.setBackground(new Color(44, 62, 80));
                }
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (lblSelectedMenu != null) {
                    menuPanels.get(lblSelectedMenu.getText()).setBackground(new Color(44, 62, 80));
                }
                lblSelectedMenu = lblMenu;
                menuPanel.setBackground(new Color(52, 73, 94));

                loadContent(menuText);
            }
        });

        return menuPanel;
    }

    public void setActiveMenu(String menuText) {
        if (lblSelectedMenu != null) {
            menuPanels.get(lblSelectedMenu.getText()).setBackground(new Color(44, 62, 80));
        }

        if (menuPanels.containsKey(menuText)) {
            lblSelectedMenu = (JLabel) menuPanels.get(menuText).getComponent(0);
            menuPanels.get(menuText).setBackground(new Color(52, 73, 94));
        }
    }

    private void loadContent(String menuText) {

        MainDashboard dashboard = (MainDashboard) parentFrame;

        switch (menuText) {
            case "DASHBOARD":
                dashboard.showWelcomeScreen();
                break;

            case "CATEGORIES":
                JPanel categoryPanel = createCategoryPanel();
                dashboard.updateContent(categoryPanel);
                break;

            case "PRODUCTS":
                JPanel productPanel = createProductPanel();
                dashboard.updateContent(productPanel);
                break;

            case "SUPPLIERS":
                JPanel suppliersPanel = createPlaceholderPanel("Supplier Management");
                dashboard.updateContent(suppliersPanel);
                break;

            case "CUSTOMERS":
                JPanel customersPanel = createPlaceholderPanel("Customer Management");
                dashboard.updateContent(customersPanel);
                break;

            case "USERS":
                JPanel userJPanel= createUserPanel();
                dashboard.updateContent(userJPanel);
                break;

            case "IMPORT PRODUCTS":
                JPanel importPanel = createPlaceholderPanel();
                dashboard.updateContent(importPanel);
                break;

            case "EXPORT PRODUCTS":
                JPanel exportPanel = createPlaceholderPanel("Export Products");
                dashboard.updateContent(exportPanel);
                break;

            case "Logout":
                int confirm = JOptionPane.showConfirmDialog(parentFrame,
                        "Are you sure you want to logout?",
                        "Confirm Logout",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
    }

    private JPanel createPlaceholderPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(title + " (Coming Soon)");
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setHorizontalAlignment(JLabel.CENTER);

        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

}