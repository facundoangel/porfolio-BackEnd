/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.User;
import com.projectApi.demo.repository.RepositoryUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author facun
 */
@Service
public class UserService {
    
    @Autowired
    RepositoryUser us;
    
    public User findUserByName(String pass, String Username){
        List<User> Users = new ArrayList<>();
        Users = us.findAll();
        User UserFound = null;
        
        
      
        //System.out.println(Username);
        //System.out.println(pass);
        for(User user : Users){
            
            if(user.getUserName().equals(Username) && user.getPass().equals(pass)){
                return user;
            }
            
            
        }
        
        return UserFound;

    }
    
    public User findUser(Long id){
        return us.findById(id).orElse(null);
    }
    
    public void saveUser(User usr){
        us.save(usr);
    }
    
}
