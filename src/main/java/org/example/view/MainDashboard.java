package org.example.view;

import org.example.view.component.SidebarMenu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;
    private JLabel welcomeLabel;

    public MainDashboard() {
        initComponents();
    }

    private void initComponents() {

        setTitle("Warehouse Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        SidebarMenu sidebarMenu = new SidebarMenu(this);
        mainPanel.add(sidebarMenu, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        welcomeLabel = new JLabel("Welcome to Warehouse Management System");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    public void updateContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showWelcomeScreen() {
        contentPanel.removeAll();
        contentPanel.add(welcomeLabel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}