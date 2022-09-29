/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.model;

import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author facun
 */
@Getter @Setter
@Entity
public class RelationProjectTecnologies {
   @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
     Long idProject;
     Long idTech;
     
     
    public RelationProjectTecnologies(){}; 

    public RelationProjectTecnologies(Long idProject, Long idTech) {
        this.idProject = idProject;
        this.idTech = idTech;
    }
    
    public Long getidProject() {
        return idProject;
    }
    
    public Long getidTech() {
        return idTech;
    }
    
}
