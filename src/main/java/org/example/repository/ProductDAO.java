package org.example.repository;

import jakarta.persistence.EntityManager;
import org.example.constant.ErrorMessage;
import org.example.domain.model.ProductModel;
import org.example.utils.HibernateUtils;

import java.util.List;

public class ProductDAO {

    private EntityManager getEntityManager() {
        return HibernateUtils.getEntityManager();
    }

    public void createProduct(ProductModel product) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.Product.ERR_CREATE_PRODUCT);
        } finally {
            em.close();
        }
    }

    public void updateProduct(Long id, ProductModel product) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ProductModel existingProduct = em.find(ProductModel.class, id);
            if (existingProduct == null) {
                throw new RuntimeException(String.format(ErrorMessage.Product.ERR_GET_BY_ID_PRODUCT, id));
            }
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setRemainingQuantity(product.getRemainingQuantity());
            existingProduct.setSoldQuantity(product.getSoldQuantity());
            existingProduct.setCategoryModel(product.getCategoryModel());
            em.merge(existingProduct);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException(String.format(ErrorMessage.Product.ERR_UPDATE_PRODUCT, id));
        } finally {
            em.close();
        }
    }

    public void deleteProduct(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ProductModel product = em.find(ProductModel.class, id);
            if (product == null) {
                throw new RuntimeException(String.format(ErrorMessage.Product.ERR_GET_BY_ID_PRODUCT, id));
            }
            em.remove(product);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Product.ERR_NOT_FOUND, id));
        } finally {
            em.close();
        }
    }

    public ProductModel getProductById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ProductModel product = em.find(ProductModel.class, id);
            if (product == null) {
                throw new RuntimeException(String.format(ErrorMessage.Product.ERR_GET_BY_ID_PRODUCT, id));
            }
            em.getTransaction().commit();
            return product;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Product.ERR_NOT_FOUND, id));
        } finally {
            em.close();
        }
    }

    public List<ProductModel> getAllProducts() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<ProductModel> products = em.createQuery("SELECT p FROM ProductModel p", ProductModel.class)
                    .getResultList();
            em.getTransaction().commit();
            return products;
        } catch (RuntimeException e) {
            throw new RuntimeException(ErrorMessage.Product.ERR_NOT_FOUND);
        } finally {
            em.close();
        }
    }

}