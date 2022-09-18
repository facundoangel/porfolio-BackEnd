/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.model;

import java.util.List;


/**
 *
 * @author facun
 */

public class DTOProjectData {


    private Long id;
    private String name;
    private String photo;
    private String description;
    private String url;
    private List<String> tecnologies;

    public DTOProjectData() {
    };

    
    //CONSTRUCTOR DE LA CLASE EN BASE A DATOS DEL PROYECTO
    public DTOProjectData(String name, String photo, String description, String url, List<String> tecnologies) {
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.url = url;
        this.tecnologies = tecnologies;
    }
    
    //CONSTRUCTOR DE LA CLASE EN BASE A UNA ENTIDAD PROJECTO Y UN STRING DE TECNOLOGIAS
    public DTOProjectData(Project pro, List<String> tecnologies) {
        this.id=pro.getId();
        this.name = pro.getName();
        this.photo = pro.getPhoto();
        this.description = pro.getDescription();
        this.url = pro.getUrl();
        this.tecnologies = tecnologies;
    }
    
    
    
    
    public String getName(){
        return this.name;
    }
    
    public String getPhoto(){
        return this.photo;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getUrl(){
        return this.url;
    }
    
    public Long getId(){
        return this.id;
    }
    
    public List<String> getTecnologies(){
        return this.tecnologies;
    }
}
