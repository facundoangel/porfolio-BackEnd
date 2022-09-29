/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectApi.demo.repository;

import com.projectApi.demo.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author facun
 */
@Repository
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo,Long> {
    
}
