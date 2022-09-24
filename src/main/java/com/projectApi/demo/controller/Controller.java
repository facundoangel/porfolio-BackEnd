/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;

import com.projectApi.demo.model.DTOEstudie;
import com.projectApi.demo.model.DTOExperience;
import com.projectApi.demo.model.DTOPersonalInfo;
import com.projectApi.demo.model.DTOProject;
import com.projectApi.demo.model.DTOProjectData;
import com.projectApi.demo.model.DTOSkill;
import com.projectApi.demo.model.Estudie;
import com.projectApi.demo.model.Experience;
import com.projectApi.demo.model.PersonalInfo;
import com.projectApi.demo.model.Project;
import com.projectApi.demo.model.RelationProjectTecnologies;
import com.projectApi.demo.model.Session;
import com.projectApi.demo.model.Skill;
import com.projectApi.demo.model.User;
import com.projectApi.demo.service.PortfolioService;
import com.projectApi.demo.service.RelationProjectTecnologiesServices;
import com.projectApi.demo.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author facun
 */
@RestController
@CrossOrigin(origins = "https://porftolio-angular.web.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class Controller {
    
    
    @Autowired
    private PortfolioService port;
    @Autowired
    private UserService us;
    @Autowired
    private RelationProjectTecnologiesServices rel;


    @PostMapping("/auth")
    public ResponseEntity<Object> authUser(@RequestBody Session usr){
        
        User userFound = us.findUserByName(usr.getPass(), usr.getUserName());
        
        if(userFound!=null){
            return new ResponseEntity<>(userFound,HttpStatus.OK);
        }
    
        return new ResponseEntity<>("Usuario o contraseña incorrecta",HttpStatus.BAD_REQUEST);
    }
    
    public Boolean testSession(String tk,Long id){
        User token = us.findUser(id);
        
        if(token==null){
            return false;
        } else {
            return token.getIdentified().equals(tk);
        }        
        
        
    }
    
    //=====================================methods of Personal Info===================================
    
    @GetMapping("/info/{id}")
    public ResponseEntity<Object> getInfo(@PathVariable Long id){
        
        return new ResponseEntity<> (port.findInfo(id), HttpStatus.OK);
    }
    
    @PutMapping("/info/{id}")
    public ResponseEntity<Object> setInfo(@PathVariable Long id, @RequestBody DTOPersonalInfo requestPersinf){
        
        
        if(!this.testSession(requestPersinf.getTk(),requestPersinf.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        PersonalInfo persinf = requestPersinf.getData();
        
          if(!Objects.equals(id, persinf.getId())){
          return new ResponseEntity<>("Error: no coincide la id del recurso",HttpStatus.BAD_REQUEST);  
        }
          
          try{
            PersonalInfo toEdit = port.findInfo(id);
           toEdit.setDescription(persinf.getDescription());
            
            
            port.setInfo(toEdit);
            
        }catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso editado con exito",HttpStatus.OK);
        
    }
    
    
    //=====================================methods of Personal Info===================================
    
    
}
