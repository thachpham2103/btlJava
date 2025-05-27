package org.example.repository;

import jakarta.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.config.DatabaseConfig;
import org.example.constant.ErrorMessage;
import org.example.domain.model.CategoryModel;
import org.example.domain.model.SupplierModel;
import org.example.utils.HibernateUtils;


public class SupplierDAO{
 private EntityManager getEntityManager() {
        return HibernateUtils.getEntityManager();
    }

    public void create(SupplierModel supplierModel) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(supplierModel);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.Supplier.ERR_CREATE_SUP);
        } finally {
            em.close();
        }
    }

    public void update(Long id, SupplierModel supplierModel) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            SupplierModel existingSupplier = em.find(SupplierModel.class, id);
            if (existingSupplier == null) {
                throw new RuntimeException(String.format(ErrorMessage.Supplier.ERR_GET_BY_ID_SUP, id));
            }
            existingSupplier.setName(supplierModel.getName());
            existingSupplier.setAddress(supplierModel.getAddress());
            existingSupplier.setPhoneNumber(supplierModel.getPhoneNumber());
            existingSupplier.setEmail(supplierModel.getEmail());
         
            em.merge(existingSupplier);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(String.format(ErrorMessage.Supplier.ERR_UPDATE_SUP, id));
        } finally {
            em.close();
        }
    }

    public CategoryModel getCategoryById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CategoryModel category = em.find(CategoryModel.class, id);
            em.getTransaction().commit();
            if (category == null) {
                throw new RuntimeException(String.format(ErrorMessage.Category.ERR_GET_BY_ID_CATEGORY, id));
            }
            return category;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Category.ERR_GET_BY_ID_CATEGORY, id));
        } finally {
            em.close();
        }
    }

    public List<CategoryModel> getAllCategories() {
        EntityManager em = getEntityManager();
        List<CategoryModel> categories = new ArrayList<>();
        try {
            em.getTransaction().begin();
            categories = em.createQuery("SELECT c FROM CategoryModel c", CategoryModel.class)
                    .getResultList();
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(ErrorMessage.Category.ERR_NOT_FOUND);
        }
        return categories;
    }

    public void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            CategoryModel category = em.find(CategoryModel.class, id);
            if (category == null) {
                throw new RuntimeException(String.format(ErrorMessage.Category.ERR_GET_BY_ID_CATEGORY, id));
            }
            em.remove(category);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Category.ERR_GET_BY_ID_CATEGORY, id));
        } finally {
            em.close();
        }

    }

}