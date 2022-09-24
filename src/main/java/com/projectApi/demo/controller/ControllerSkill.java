/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;


import com.projectApi.demo.model.DTOSkill;
import com.projectApi.demo.model.Skill;
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
public class ControllerSkill {
    
    
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
    
    
    //=====================================methods of Skills======================================
   

    @GetMapping("/skills")
    public ResponseEntity<Object> getSkills(){

        return new ResponseEntity<>(port.getSkill(),HttpStatus.OK);
    }
    
    @PostMapping("/skills")
    public ResponseEntity<Object> createSkill(@RequestBody DTOSkill requestSk){
        
        if(!this.testSession(requestSk.getTk(),requestSk.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Skill sk = requestSk.getData();
        
        Long idNew;
        
        try{
            idNew = port.saveSkill(sk);
            
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(idNew,HttpStatus.OK);
    }
    
    @PutMapping("/skills/{id}")
    public ResponseEntity<Object> putSkill (@RequestBody DTOSkill requestSk,@PathVariable Long id){
        
        if(!this.testSession(requestSk.getTk(),requestSk.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Skill sk = requestSk.getData();

        if(!Objects.equals(id, sk.getId())){
          return new ResponseEntity<>("Error: no coincide la id del recurso",HttpStatus.BAD_REQUEST);  
        }
        
        try{
            Skill skToEdit = port.findSkill(id);
            skToEdit.setLevel(sk.getLevel());
            skToEdit.setName(sk.getName());
            skToEdit.setPhoto(sk.getPhoto());
            
            
            
            
            port.saveSkill(skToEdit);
            
        }catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso editado con exito",HttpStatus.OK);
    }
    
    
     @DeleteMapping("/skills/{id}/{idUsuario}/{tk}")
    public ResponseEntity<Object> deleteSkill (@PathVariable Long id, @PathVariable Long idUsuario,@PathVariable String tk){
        
       if(!this.testSession(tk,idUsuario)){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        try{
            port.deleteSkill(id);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>("Recurso eliminado con exito",HttpStatus.OK);
    }
    
    
    
    //=====================================methods of skills======================================
    
}
