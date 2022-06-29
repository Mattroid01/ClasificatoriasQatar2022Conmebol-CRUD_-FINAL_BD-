package com.example.mysql.data.service;

import com.example.mysql.data.entity.Partido;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PartidoService {

    private final PartidoRepository repository;

    @Autowired
    public PartidoService(PartidoRepository repository) {
        this.repository = repository;
    }

    public Optional<Partido> get(UUID id) {
        return repository.findById(id);
    }

    public Partido update(Partido entity) {
        return repository.save(entity);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Partido> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
