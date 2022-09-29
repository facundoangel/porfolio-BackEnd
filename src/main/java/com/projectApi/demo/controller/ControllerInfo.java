/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;


import com.projectApi.demo.model.DTOPersonalInfo;
import com.projectApi.demo.model.PersonalInfo;
import com.projectApi.demo.model.User;
import com.projectApi.demo.service.PortfolioService;
import com.projectApi.demo.service.RelationProjectTecnologiesServices;
import com.projectApi.demo.service.UserService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author facun
 */
@RestController
//@CrossOrigin(origins = "http://localhost:4200/", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "https://facundo-angel.firebaseapp.com/", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ControllerInfo {
    
    
    @Autowired
    private PortfolioService port;
    @Autowired
    private UserService us;
    @Autowired
    private RelationProjectTecnologiesServices rel;


   
    
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
    
    //==============================methods of skills======================================
    
}
