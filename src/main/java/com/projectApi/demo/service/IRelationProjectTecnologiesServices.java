/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.RelationProjectTecnologies;
import java.util.List;

/**
 *
 * @author facun
 */
public interface IRelationProjectTecnologiesServices {
   
    public List<RelationProjectTecnologies> getAllTecnologiesById(Long Id);
    public void deleteById(Long id);
    public void SaveRelationProTech (RelationProjectTecnologies rel);
    public void deleteAllRelations(List<RelationProjectTecnologies> rels);
}
