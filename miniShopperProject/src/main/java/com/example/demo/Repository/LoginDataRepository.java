package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.LoginData;


public interface LoginDataRepository extends JpaRepository<LoginData, Integer>{
          
}
