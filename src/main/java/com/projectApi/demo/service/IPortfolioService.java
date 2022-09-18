/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.Estudie;
import com.projectApi.demo.model.Experience;
import com.projectApi.demo.model.PersonalInfo;
import com.projectApi.demo.model.Project;
import com.projectApi.demo.model.Skill;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author facun
 */
public interface IPortfolioService {
    
    public List<Estudie> getEstudies();
    public Long saveEstudie(Estudie estudie);
    public void deleteEstudie(Long id);
    public Estudie findEstudie(Long id);
    
    

    public List<Experience> getExperience();
    public Long saveExperience(Experience experience);
    public void deleteExperience(Long id);
    public Experience findExperience(Long id);
    
    
    
    public List<Project> getProject();
    public Long saveProject(Project project);
    public void deleteProject(Long id);
    public Project findProject(Long id);
    
    
    public List<Skill> getSkill();
    public Long saveSkill(Skill skill);
    public void deleteSkill(Long id);
    public Skill findSkill(Long id);
    
    
    public List<PersonalInfo> getInfo();
    public void setInfo(PersonalInfo param);
    public PersonalInfo findInfo(Long Id);
}
