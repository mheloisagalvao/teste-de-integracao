package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Você não precisa adicionar nenhum método aqui, pois o Spring Data JPA
    // fornece automaticamente os métodos de CRUD (save, findAll, findById, deleteById, etc.).
}
