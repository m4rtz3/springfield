package com.example.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadaoRepository extends CrudRepository<CidadaoEntity, Integer> {
    // Métodos CRUD já estão disponíveis
}
