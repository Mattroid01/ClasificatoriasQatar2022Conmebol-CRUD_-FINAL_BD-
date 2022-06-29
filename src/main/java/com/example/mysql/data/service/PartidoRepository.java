package com.example.mysql.data.service;

import com.example.mysql.data.entity.Partido;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidoRepository extends JpaRepository<Partido, UUID> {

}