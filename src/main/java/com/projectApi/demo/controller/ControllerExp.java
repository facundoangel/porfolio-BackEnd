/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;


import com.projectApi.demo.model.DTOExperience;
import com.projectApi.demo.model.Experience;
import com.projectApi.demo.model.User;
import com.projectApi.demo.service.PortfolioService;
import com.projectApi.demo.service.RelationProjectTecnologiesServices;
import com.projectApi.demo.service.UserService;
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
public class ControllerExp {
    
    
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
    
    
    //=====================================methods of Experiences======================================
   

    @GetMapping("/experiences")
    public ResponseEntity<Object> getExperience(){

        return new ResponseEntity<>(port.getExperience(),HttpStatus.OK);
    }
    

    
    @PostMapping("/experiences")
    public ResponseEntity<Object> createExperience(@RequestBody DTOExperience requestExp) throws Exception{
        
        
        if(!this.testSession(requestExp.getTk(),requestExp.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Experience exp = requestExp.getData();
        
        
  
        
        Long idNew; 
        
        try{
            exp.setId(null);
            idNew = port.saveExperience(exp);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
        }
    
        return new ResponseEntity<>(idNew,HttpStatus.OK);
    }
    
    @PutMapping("/experiences/{id}")
    public ResponseEntity<Object> putExperience(@RequestBody DTOExperience requestExp,@PathVariable Long id){
        
        if(!this.testSession(requestExp.getTk(),requestExp.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Experience exp = requestExp.getData();
        
        if(!Objects.equals(id, exp.getId())){
          return new ResponseEntity<>("Error: no coincide la id del recurso",HttpStatus.BAD_REQUEST);  
        }
        
        try{
            Experience expToEdit = port.findExperience(id);
            expToEdit.setCompany(exp.getCompany());
            expToEdit.setDateEnd(exp.getDateEnd());
            expToEdit.setDateStart(exp.getDateStart());
            expToEdit.setPosition(exp.getPosition());
            expToEdit.setTasks(exp.getTasks());
            
            
            port.saveExperience(expToEdit);
            
        }catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso editado con exito",HttpStatus.OK);
    }
    
    
     @DeleteMapping("/experiences/{id}/{idUsuario}/{tk}")
    public ResponseEntity<Object> deleteExperience(@PathVariable Long id, @PathVariable Long idUsuario,@PathVariable String tk){
        
        if(!this.testSession(tk,idUsuario)){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
       
        
        try{
            port.deleteExperience(id);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso eliminado con exito",HttpStatus.OK);
    }
    
    
    
    //=====================================methods of Experiences======================================
    
    
}
