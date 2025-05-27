package org.example.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.example.constant.ErrorMessage;
import org.example.domain.model.UserModel;
import org.example.utils.HibernateUtils;

public class UserDAO {

    private EntityManager getEntityManager() {
        return HibernateUtils.getEntityManager();
    }

    public void create(UserModel userModel) {
        try (EntityManager en = getEntityManager()) {
            en.getTransaction().begin();
            en.persist(userModel);
            en.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.User.ERR_CREATE_USER, e);
        }
    }

    public void update(Long id, UserModel userModel) {
        try (EntityManager en = getEntityManager()) {
            en.getTransaction().begin();
            UserModel existingUser = en.find(UserModel.class, id);
            if (existingUser == null) {
                throw new RuntimeException(String.format(ErrorMessage.User.ERR_GET_BY_ID_USER, id));
            }
            existingUser.setUsername(userModel.getUsername());
            existingUser.setAddress(userModel.getAddress());
            existingUser.setEmail(userModel.getEmail());
            existingUser.setFullName(userModel.getFullName());
            existingUser.setCreatedAt(userModel.getCreatedAt());
            existingUser.setPhoneNumber(userModel.getPhoneNumber());
            existingUser.setPassword(userModel.getPassword());
            existingUser.setUpdatedAt(userModel.getUpdatedAt());
            existingUser.setStatus(userModel.getStatus());
            en.merge(existingUser);
            en.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(String.format(ErrorMessage.User.ERR_UPDATE_USER, id), e);
        }
    }

    public UserModel getUserById(Long id) {
        try (EntityManager en = getEntityManager()) {
            UserModel userModel = en.find(UserModel.class, id);
            if (userModel == null) {
                throw new RuntimeException(String.format(ErrorMessage.User.ERR_GET_BY_ID_USER, id));
            }
            return userModel;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ErrorMessage.User.ERR_GET_BY_ID_USER, id), e);
        }
    }

    public List<UserModel> getAllUser() {
        try (EntityManager en = getEntityManager()) {
            return en.createQuery("SELECT u FROM UserModel u", UserModel.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.User.ERR_NOT_FOUND, e);
        }
    }

    public void delete(Long id) {
        try (EntityManager en = getEntityManager()) {
            en.getTransaction().begin();
            UserModel userModel = en.find(UserModel.class, id);
            if (userModel == null) {
                throw new RuntimeException(String.format(ErrorMessage.User.ERR_GET_BY_ID_USER, id));
            }
            en.remove(userModel);
            en.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(String.format(ErrorMessage.User.ERR_GET_BY_ID_USER, id), e);
        }
    }

    public boolean existsByEmail(String email) {
        try (EntityManager en = getEntityManager()) {
            Long count = en.createQuery("SELECT COUNT(u) FROM UserModel u WHERE u.email = :email", Long.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ErrorMessage.User.ERR_CHECK_EMAIL, email), e);
        }
    }

    public boolean existsByUsername(String username) {
        try (EntityManager en = getEntityManager()) {
            Long count = en.createQuery("SELECT COUNT(u) FROM UserModel u WHERE u.username = :username", Long.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException(String.format(ErrorMessage.User.ERR_CHECK_USERNAME, username), e);
        }
    }
}