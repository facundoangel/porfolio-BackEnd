/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.service;

import com.projectApi.demo.model.RelationProjectTecnologies;
import com.projectApi.demo.repository.RelationProjectTecnologiesRepo;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author facun
 */
@Service
public class RelationProjectTecnologiesServices implements IRelationProjectTecnologiesServices{
    
    @Autowired
    RelationProjectTecnologiesRepo service;

    @Override
    public List<RelationProjectTecnologies> getAllTecnologiesById(Long Id) {
        Stream<RelationProjectTecnologies> Lista = service.findAll().stream().filter(x -> Objects.equals(x.getidProject(), Id));
        
        return Lista.toList();
        
    }
    
    @Override
    public void deleteById(Long id){
        
        service.deleteById(id);
    }

    @Override
    public void SaveRelationProTech(RelationProjectTecnologies rel) {
        service.save(rel);
    }

    @Override
    public void deleteAllRelations(List<RelationProjectTecnologies> rels) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
}
