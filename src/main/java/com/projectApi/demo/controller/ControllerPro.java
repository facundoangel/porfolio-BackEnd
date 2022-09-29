/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.controller;


import com.projectApi.demo.model.DTOProject;
import com.projectApi.demo.model.DTOProjectData;
import com.projectApi.demo.model.Project;
import com.projectApi.demo.model.RelationProjectTecnologies;
import com.projectApi.demo.model.User;
import com.projectApi.demo.service.PortfolioService;
import com.projectApi.demo.service.RelationProjectTecnologiesServices;
import com.projectApi.demo.service.UserService;
import java.util.ArrayList;
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
//@CrossOrigin(origins = "http://localhost:4200/", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@CrossOrigin(origins = "https://facundo-angel.firebaseapp.com/", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ControllerPro {
    
    
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
    
    //=====================================methods of Projects======================================
   

    @GetMapping("/projects")
    public ResponseEntity<Object> getProjects(){

        List<Project> projects = port.getProject();
        List<DTOProjectData> DTOProjects= new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        
        
        for(Project proj : projects){
            //ids.add(proj.getId());
            List<RelationProjectTecnologies> Relations = rel.getAllTecnologiesById(proj.getId());
             List<String> technologies = port.getSkillThrougthList(Relations);
            DTOProjects.add(new DTOProjectData(proj,technologies));
            
            
        }
        
        /*List<RelationProjectTecnologies> Relations2 = rel.getAllTecnologiesById(Long.parseLong("2"));
        List<String> technologies = port.getSkillThrougthList(Relations2);*/
        
        return new ResponseEntity<>(DTOProjects,HttpStatus.OK);
    }
    
   
    @PostMapping("/projects")
    public ResponseEntity<Object> createProject(@RequestBody DTOProject requestpro){
        
        
        if(!this.testSession(requestpro.getTk(),requestpro.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        

        //CREO EL NUEVO PROYECTO A GUARDAR EN LA BBDD
        Project pro;
        pro = new Project(
                requestpro.getData().getName(),
                requestpro.getData().getPhoto(),
                requestpro.getData().getDescription(),
                requestpro.getData().getUrl()
        );
        
 
        
        
        Long idNew;
        try{
            idNew = port.saveProject(pro);
            
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
        }
        
        
       List<String> technologies = requestpro.getData().getTecnologies();
        //GUARDO LAS TECNOLOGIAS USADAS EN EL PROYECTO EN LA TABLA AUXILIAR DE LA BBDD
        for(String tech : technologies){
            Long idTech = Long.parseLong(tech);
           
            
            try{
                rel.SaveRelationProTech(new RelationProjectTecnologies(idNew,idTech));
            
            } catch(Exception e){
                return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
            }
        }
        
        
        return new ResponseEntity<>(idNew,HttpStatus.OK);
        }
    
    @PutMapping("/projects/{id}")
    public ResponseEntity<Object> putProject (@RequestBody DTOProject requestpro,@PathVariable Long id){
        
        if(!this.testSession(requestpro.getTk(),requestpro.getId())){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        Project pro;
        pro = new Project(
                requestpro.getData().getName(),
                requestpro.getData().getPhoto(),
                requestpro.getData().getDescription(),
                requestpro.getData().getUrl()
        );
        
        
        
        if(!Objects.equals(id, requestpro.getData().getId())){
          return new ResponseEntity<>("Error: no coincide la id del recurso",HttpStatus.BAD_REQUEST);  
        }
        
        //ACTUALIZO LA ENTIDAD PROYECTO
        try{
            Project proToEdit = port.findProject(id);
            proToEdit.setDescription(pro.getDescription());
            proToEdit.setName(pro.getName());
            proToEdit.setPhoto(pro.getPhoto());
            proToEdit.setUrl(pro.getUrl());
            
            
            
            port.saveProject(proToEdit);
            
        }catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        
        //ACTUALIZO LA TABLA AUXILIAR CON LAS RELAIONES ENTRE LAS ENTIDADES
        //PROYECTOS Y SKILL
        
        List<RelationProjectTecnologies> TechToDelete = rel.getAllTecnologiesById(id);
        //GUARDO LAS TECNOLOGIAS USADAS EN EL PROYECTO EN LA TABLA AUXILIAR DE LA BBDD
        for(RelationProjectTecnologies tech : TechToDelete){
            Long idTech = tech.getId();
           
            
            try{
                
                rel.deleteById(idTech);
            
            } catch(Exception e){
                return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
            }
        }
        
        
        List<String> TechToAdd = requestpro.getData().getTecnologies();
        //GUARDO LAS TECNOLOGIAS USADAS EN EL PROYECTO EN LA TABLA AUXILIAR DE LA BBDD
        for(String tech : TechToAdd){
            Long idTech = Long.parseLong(tech);
           
            
            try{
                rel.SaveRelationProTech(new RelationProjectTecnologies(id,idTech));
            
            } catch(Exception e){
                return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
            }
        }
        
        
       
        return new ResponseEntity<>("Recurso editado con exito",HttpStatus.OK);
    }
    
    
     @DeleteMapping("/projects/{id}/{idUsuario}/{tk}")
    public ResponseEntity<Object> deleteProject (@PathVariable Long id, @PathVariable Long idUsuario,@PathVariable String tk){
        
         if(!this.testSession(tk,idUsuario)){
            return new ResponseEntity<>("Error: Credenciales no validas",HttpStatus.FORBIDDEN);
        } 
        
        
        try{
            port.deleteProject(id);
        } catch(Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        
        
        List<RelationProjectTecnologies> technologies = rel.getAllTecnologiesById(id);
        //GUARDO LAS TECNOLOGIAS USADAS EN EL PROYECTO EN LA TABLA AUXILIAR DE LA BBDD
        for(RelationProjectTecnologies tech : technologies){
            Long idTech = tech.getId();
           
            
            try{
                
                rel.deleteById(idTech);
            
            } catch(Exception e){
                return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.OK);
            }
        }
        
        return new ResponseEntity<>("Recurso eliminado con exito",HttpStatus.OK);
    }
    
    
    
    //=====================================methods of Projects======================================
    
    
    
    //=====================================methods of skills======================================
    
}
