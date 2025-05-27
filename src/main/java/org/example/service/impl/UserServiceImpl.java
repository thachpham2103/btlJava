/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.service.impl;

import jakarta.persistence.EntityManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.example.domain.model.UserModel;
import org.example.repository.UserDAO;
import org.example.service.UserService;

/**
 *
 * @author ADMIN
 */
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO = new UserDAO();
    
    @Override
    public boolean createUser(UserModel userModel) {
        try {
            userDAO.create(userModel);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace(); // Ghi log nếu cần
            return false;
        }
    }

    @Override
    public boolean updateUser(Long id, UserModel userModel) {
        try {
            userDAO.update(id, userModel);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(Long id) {
        try {
            userDAO.delete(id);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserModel> getAllUsers() {
        try {
            return userDAO.getAllUser();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserModel getUserById(Long id) {
        try {
            return userDAO.getUserById(id);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
  try {
            return userDAO.existsByEmail(email);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }    }

    @Override
    public boolean existsByUsername(String username) {
  try {
            return userDAO.existsByUsername(username);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }  }
    
}
