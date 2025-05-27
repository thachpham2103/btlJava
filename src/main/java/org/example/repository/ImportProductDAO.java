package org.example.repository;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.constant.ErrorMessage;
import org.example.domain.model.CategoryModel;
import org.example.domain.model.ImportProductModel;
import org.example.utils.HibernateUtils;

public class ImportProductDAO  {

     private EntityManager getEntityManager() {
        return HibernateUtils.getEntityManager();
    }

    public void create(ImportProductModel importProductModel) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(importProductModel);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.Import.ERR_CREATE_IMP);
        } finally {
            em.close();
        }
    }

    public void update(Long id, ImportProductModel importProductModel) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            ImportProductModel existingImport= em.find(ImportProductModel.class, id);
            if (existingImport == null) {
                throw new RuntimeException(String.format(ErrorMessage.Import.ERR_GET_BY_ID_IMP, id));
            }
            existingImport.setImportDate(LocalDate.now());
            existingImport.setUserModel(importProductModel.getUserModel());
            existingImport.setSupplierModel(importProductModel.getSupplierModel());
            existingImport.setImportDetails(importProductModel.getImportDetails());
            
            em.merge(existingImport);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(String.format(ErrorMessage.Import.ERR_UPDATE_IMP_, id));
        } finally {
            em.close();
        }
    }

    
    public ImportProductModel getImportPoroductById(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ImportProductModel importProductModel = em.find(ImportProductModel.class, id);
            em.getTransaction().commit();
            if (importProductModel == null) {
                throw new RuntimeException(String.format(ErrorMessage.Import.ERR_GET_BY_ID_IMP, id));
            }
            return importProductModel;
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Import.ERR_GET_BY_ID_IMP, id));
        } finally {
            em.close();
        }
    }

    
    public List<ImportProductModel> getAllImportProducts() {
        EntityManager em = getEntityManager();
        List<ImportProductModel> importProductModels = new ArrayList<>();
        try {
            em.getTransaction().begin();
            importProductModels = em.createQuery("SELECT c FROM ImportProductModel c", ImportProductModel.class)
                    .getResultList();
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(ErrorMessage.Import.ERR_NOT_FOUND);
        }
        return importProductModels;
    }

    public void delete(Long id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            ImportProductModel importProductModel = em.find(ImportProductModel.class, id);
            if (importProductModel == null) {
                throw new RuntimeException(String.format(ErrorMessage.Import.ERR_GET_BY_ID_IMP, id));
            }
            em.remove(importProductModel);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            throw new RuntimeException(String.format(ErrorMessage.Import.ERR_GET_BY_ID_IMP, id));
        } finally {
            em.close();
        }
    }
}
