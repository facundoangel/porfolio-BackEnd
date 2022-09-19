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
@CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
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
