package com.alura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.literalura.model.Libro;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>
{

    @Query("SELECT l FROM Libro l WHERE l.lenguaje ILIKE %:lenguaje%")
    List<Libro> findByLenguaje(String lenguaje);
}