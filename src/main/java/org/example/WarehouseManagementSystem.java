package org.example;

import org.example.view.MainDashboard;

import javax.swing.*;

public class WarehouseManagementSystem {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error initializing application look and feel: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        SwingUtilities.invokeLater(() -> {
            new MainDashboard().setVisible(true);
        });
    }
}