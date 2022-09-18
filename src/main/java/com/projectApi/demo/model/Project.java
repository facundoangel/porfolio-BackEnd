/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.model;

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
public class Project {
    
     @Id
   @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String photo;
    private String description;
    private String url;
    
    public Project(){};

    public Project(String name, String photo, String description, String url) {
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.url = url;
    }

    
}
