/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.Estudie;
import com.projectApi.demo.model.Experience;
import com.projectApi.demo.model.PersonalInfo;
import com.projectApi.demo.model.Project;
import com.projectApi.demo.model.RelationProjectTecnologies;
import com.projectApi.demo.model.Skill;
import com.projectApi.demo.repository.EstudiesRepository;
import com.projectApi.demo.repository.ExperienceRepository;
import com.projectApi.demo.repository.PersonalInfoRepository;
import com.projectApi.demo.repository.ProjectRepository;
import com.projectApi.demo.repository.SkillRepository;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author facun
 */
@Service
public class PortfolioService implements IPortfolioService {

    
    @Autowired
    EstudiesRepository est;
    @Autowired
    ExperienceRepository exp;
    @Autowired
    ProjectRepository proj;
    @Autowired
    SkillRepository skill;
    @Autowired
    PersonalInfoRepository pi;
    

    
    
    //Personal info  methods======================================================
    
    @Override
    public List<PersonalInfo> getInfo(){
        return pi.findAll();
    }
    
    @Override
    public void setInfo(PersonalInfo param){
        pi.save(param);
    }
    
    @Override
    public PersonalInfo findInfo(Long Id){
        return pi.findById(Id).orElse(null);
    }
    
    //Personal info  methods======================================================
    
    
    //estudies methods============================================================
    @Override
    public List<Estudie> getEstudies() {
        return est.findAll();
    }

    @Override
    public Long saveEstudie(Estudie estudie) {
      return est.save(estudie).getId();
      
    }

    @Override
    public void deleteEstudie(Long id) {
        est.deleteById(id);
    }

    @Override
    public Estudie findEstudie(Long id) {
        return est.findById(id).orElse(null);
    }

    //estudies methods============================================================
    
    //experiences methods============================================================
   
    
    @Override
    public List<Experience> getExperience() {
        return exp.findAll();
    }

    @Override
    public Long saveExperience(Experience experience) {
        return exp.save(experience).getId();
    }

    @Override
    public void deleteExperience(Long id) {
        exp.deleteById(id);
    }

    @Override
    public Experience findExperience(Long id) {
        return exp.findById(id).orElse(null);
    }
    
     //experiences methods============================================================

    //project methods============================================================
    @Override
    public List<Project> getProject() {
        return proj.findAll();
    }

    @Override
    public Long saveProject(Project project) {
        return proj.save(project).getId();
    }

    @Override
    public void deleteProject(Long id) {
        proj.deleteById(id);
    }

    @Override
    public Project findProject(Long id) {
        return proj.findById(id).orElse(null);
    }
    //project methods============================================================
    
    //skills methods============================================================
    @Override
    public List<Skill> getSkill() {
        return skill.findAll();
    }
    
    public Skill findSk(List<Skill> data,Long id){
        for(Skill sk : data){
            if(sk.getId().equals(id))
                return sk;
        }
        
        return new Skill("nulo");
    }

    
    public List<String> getSkillThrougthList(List<RelationProjectTecnologies> Relations) {
        List<Skill> dataSkill = skill.findAll();
        List<String> tech = new ArrayList<>();
        
        
              
        
       for(RelationProjectTecnologies rel : Relations){
            long idTech = rel.getidTech();
            Skill soughtSkill = findSk(dataSkill,idTech);
           
            if(!soughtSkill.getName().equals("nulo")){
                tech.add(soughtSkill.getPhoto());
                //return tech;
            }
            
            
          
            
        }
        
        /*long idTech = 100;
            Skill soughtSkill = skill.findById(idTech).orElse(new Skill("nulo"));
           
            if(!soughtSkill.getName().equals("nulo")){
                tech.add(soughtSkill.getPhoto());
                
            }*/
        

       
        
        return tech;
        
    }

    @Override
    public Long saveSkill(Skill sk) {
        return skill.save(sk).getId();
    }

    @Override
    public void deleteSkill(Long id) {
        skill.deleteById(id);
    }

    @Override
    public Skill findSkill(Long id) {
        return skill.findById(id).orElse(null);
    }
    //skills methods============================================================
    
}
