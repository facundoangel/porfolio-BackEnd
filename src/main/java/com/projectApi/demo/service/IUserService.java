/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.User;

/**
 *
 * @author facun
 */
public interface IUserService {
    
    
    public User finUserBy(String pass, String Username);
    public User findUser(Long id);
    public void saveUser(User usr);
}
