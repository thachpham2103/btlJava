 package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.constant.ErrorMessage;
import org.example.domain.model.CategoryModel;
import org.example.utils.HibernateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private EntityManager getEntityManager() {
        return HibernateUtils.getEntityManager();
    }

    public void create(CategoryModel categoryModel) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(categoryModel);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.Category.ERR_CREATE_CATEGORY);
        } finally {
            em.close();
        }
    }

    public void update(Long id, CategoryModel categoryModel) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            CategoryModel existingCategory = em.find(CategoryModel.class, id);
            if (existingCategory == null) {
                throw new RuntimeException(String.format(ErrorMessage.Category.ERR_GET_BY_ID_CATEGORY, id));
            }
            existingCategory.setName(categoryModel.getName());
            existingCategory.setUpdatedAt(LocalDate.now());
            em.merge(existingCategory);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(String.format(ErrorMessage.Category.ERR_UPDATE_CATEGORY, id));
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
