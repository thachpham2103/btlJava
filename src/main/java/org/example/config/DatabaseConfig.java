package org.example.config;

import org.example.utils.HibernateUtils;

import jakarta.persistence.EntityManager;

public class DatabaseConfig {
 
    public static boolean initializeDatabase() {
        EntityManager em = null;
        try {
            em = HibernateUtils.getEntityManager();
            em.getTransaction().begin();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.err.println("Không thể kết nối cơ sở dữ liệu:");
            e.printStackTrace();
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    public static void closeDatabase() {
        HibernateUtils.shutdown();
    }
}