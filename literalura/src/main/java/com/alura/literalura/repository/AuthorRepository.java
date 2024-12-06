package com.alura.literalura.repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>
{
    @Query("SELECT a FROM Author a WHERE a.nacimiento >= :anoBusqueda ORDER BY a.nacimiento ASC ")
    List<Author> autorPorFecha(int anoBusqueda);

}