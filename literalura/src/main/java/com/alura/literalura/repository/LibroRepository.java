package com.alura.literalura.repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long>
{

    @Query("SELECT l FROM Libro l WHERE l.lenguaje ILIKE %:lenguaje%")
    List<Libro> findByLenguaje(String lenguaje);
}