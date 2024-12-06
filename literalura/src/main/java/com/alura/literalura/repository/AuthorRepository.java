package com.alura.literalura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alura.literalura.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>
{
    @Query("SELECT a FROM Author a WHERE a.nacimiento >= :anoBusqueda ORDER BY a.nacimiento ASC ")
    List<Author> autorPorFecha(int anoBusqueda);

}