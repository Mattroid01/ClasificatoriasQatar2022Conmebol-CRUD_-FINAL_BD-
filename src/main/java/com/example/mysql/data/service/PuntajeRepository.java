package com.example.mysql.data.service;

import com.example.mysql.data.entity.Puntaje;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntajeRepository extends JpaRepository<Puntaje, UUID> {

}