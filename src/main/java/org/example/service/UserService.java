/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.example.service;

import java.util.List;
import org.example.domain.model.CategoryModel;
import org.example.domain.model.UserModel;

/**
 *
 * @author ADMIN
 */
public interface UserService {
    boolean createUser(UserModel userModel);
    boolean updateUser(Long id, UserModel userModel);
    boolean deleteUser(Long id);
    List<UserModel> getAllUsers();
    UserModel getUserById(Long id);

    public boolean existsByEmail(String email);

    public boolean existsByUsername(String username);
}

