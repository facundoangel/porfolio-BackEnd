/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;

import com.projectApi.demo.model.DTOEstudie;
import com.projectApi.demo.model.Estudie;
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
public class ControllerEdu {
    
    
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
    
    //=====================================methods of Educations======================================
   

    @GetMapping("/educations")
    public ResponseEntity<Object> getEstudies(){

        return new ResponseEntity<>(port.getEstudies(),HttpStatus.OK);
    }
    
    @PostMapping("/educations")
    public ResponseEntity<Object> createEducation(@RequestBody DTOEstudie requestEdu){
        
        if(!this.testSession(requestEdu.getTk(),requestEdu.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Estudie edu = requestEdu.getData();
        
        
        
        Long idNew; 
        
        try{
   
            idNew = port.saveEstudie(edu);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(idNew,HttpStatus.OK);
    }
    
    @PutMapping("/educations/{id}")
    public ResponseEntity<Object> putEducation(@RequestBody DTOEstudie requestEdu,@PathVariable Long id){
        
                if(!this.testSession(requestEdu.getTk(),requestEdu.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Estudie edu = requestEdu.getData();
        
        
        
        if(!Objects.equals(id, edu.getId())){
          return new ResponseEntity<>("Error: no coincide la id del recurso",HttpStatus.BAD_REQUEST);  
        }
        
        try{
            Estudie eduToEdit = port.findEstudie(id);
            eduToEdit.setDateEnd(edu.getDateEnd());
            eduToEdit.setDateStart(edu.getDateStart());
            eduToEdit.setInstitution(edu.getInstitution());
            eduToEdit.setName(edu.getName());
            eduToEdit.setTitle(edu.getTitle());
            
            
            port.saveEstudie(eduToEdit);
            
        }catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso editado con exito",HttpStatus.OK);
    }
    
    
     @DeleteMapping("/educations/{id}/{idUsuario}/{tk}")
    public ResponseEntity<Object> deleteEducation (@PathVariable Long id, @PathVariable Long idUsuario,@PathVariable String tk){
        
        if(!this.testSession(tk,idUsuario)){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        
        
        
        try{
            port.deleteEstudie(id);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso eliminado con exito",HttpStatus.OK);
    }
    
    
    
    //=====================================methods of Educations======================================
    
    
}
