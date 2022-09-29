/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.model;

/**
 *
 * @author facun
 */
public class DTOExperience {
    
    Experience data;
    String tk;
    Long id;
    
    
    public DTOExperience(Experience data, String tk, Long id) {
        this.data = data;
        this.tk = tk;
        this.id = id;
    }

    public Experience getData() {
        return data;
    }

    public String getTk() {
        return tk;
    }

    public Long getId() {
        return id;
    }

    
    
    
    
}
