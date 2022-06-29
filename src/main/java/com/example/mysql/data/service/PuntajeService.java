package com.example.mysql.data.service;

import com.example.mysql.data.entity.Puntaje;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PuntajeService {

    private final PuntajeRepository repository;

    @Autowired
    public PuntajeService(PuntajeRepository repository) {
        this.repository = repository;
    }

    public Optional<Puntaje> get(UUID id) {
        return repository.findById(id);
    }

    public Puntaje update(Puntaje entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Puntaje> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
