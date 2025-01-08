package com.alura.literalura.principal;

import java.util.List;
import java.util.Scanner;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Datos;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

public class Principal {
    private ConsumoAPI requestAPI = new ConsumoAPI();
    private Scanner scanner = new Scanner(System.in);
    private String urlBase ="https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AuthorRepository authorRepository;
    private List<Libro> libros;
    private List<Author> autores;

    public Principal(LibroRepository libroRepository, AuthorRepository authorRepository) {
        this.libroRepository = libroRepository;
        this.authorRepository = authorRepository;
    }

    public void muestraMenu()
    {
        var opcion = -1;
        while (opcion != 0){
            var menu ="""
                    **************************************************
                        LiterAlura - Busqueda de Libros y Autores
                    **************************************************
                    
                    Selecciona una opcion acontinuacion: 
                    
                    1 - Buscar un libro
                    2 - Consultar libros buscados
                    3 - Consultar autores
                    4 - Consultar autores de un año especifico
                    5 - Consultar libros por lenguaje
                     
                    0 - Salir               
                    """;

            try {
                System.out.println(menu);
                opcion = scanner.nextInt();
                scanner.nextLine();
            }catch (Exception e){

                System.out.println("Ingresa una opcion valida");
            }

            switch (opcion){
                case 1:
                    buscarLibro();
                    break;
                case 2:
                    consultarLibros();
                    break;
                case 3:
                    consultarAutores();
                    break;
                case 4:
                    consultarAutoresPorAno();
                    break;
                case 5:
                    consultarLibrosLenguaje();
                    break;
                case 0:
                    System.out.println("Hasta luego");
                    break;
                default:
                    System.out.println("Ingresa una opcion valida");
            }
        }
    }


    private Datos getDatosLibro() {
        System.out.println("Ingrese el nombre del libro");
        var busqueda = scanner.nextLine().toLowerCase().replace(" ","%20");
        var json = requestAPI.obtenerDatos(urlBase +
                "?search=" +
                busqueda);

        Datos datosLibro = convierteDatos.obtenerDatos(json, Datos.class);
        return datosLibro;
    }

    private void buscarLibro()
    {
        Datos datosLibro = getDatosLibro();

        try {
            Libro libro = new Libro(datosLibro.resultados().get(0));
            Author author = new Author(datosLibro.resultados().get(0).autorList().get(0));

            System.out.println("""
                    libro[
                        titulo: %s
                        author: %s
                        lenguaje: %s
                        descargas: %s
                    ]
                    """.formatted(libro.getTitulo(),
                    libro.getAutor(),
                    libro.getLenguaje(),
                    libro.getDescargas().toString()));

            libroRepository.save(libro);
            authorRepository.save(author);

        }catch (Exception e){
            System.out.println("no se encontro ese libro");
        }

    }

    private void consultarLibros() {
        libros = libroRepository.findAll();
        libros.stream().forEach(l -> {
            System.out.println("""    
                        Titulo: %s
                        Author: %s
                        Lenguaje: %s
                        Descargas: %s
                    """.formatted(l.getTitulo(),
                    l.getAutor(),
                    l.getLenguaje(),
                    l.getDescargas().toString()));
        });
    }

    private void consultarAutores() {
        autores = authorRepository.findAll();
        autores.stream().forEach(a -> {
            System.out.println("""
                        Autor: %s
                        Año de nacimiento: %s
                        Año de defuncion: %s
                    """.formatted(a.getAutor(),
                    a.getNacimiento().toString(),
                    a.getDefuncion().toString()));
        });
    }


    public void consultarAutoresPorAno()
    {
        System.out.println("Ingresa el año a partir del cual buscar:");
        var anoBusqueda = scanner.nextInt();
        scanner.nextLine();

        List<Author> authors = authorRepository.autorPorFecha(anoBusqueda);
        authors.forEach( a -> {
            System.out.println("""
                    Nombre: %s
                    Fecha de nacimiento: %s
                    Fecha de defuncion: %s
                    """.formatted(a.getAutor(),a.getNacimiento().toString(),a.getDefuncion().toString()));
        });
    }


    private void consultarLibrosLenguaje()
    {
        System.out.println("""
                ****************************************************************    
                    Selcciona el lenguaje de los libros que deseas consultar
                ****************************************************************
                1 - En (Ingles)
                2 - Es (Español)
                """);

        try {

            var opcion2 = scanner.nextInt();
            scanner.nextLine();

            switch (opcion2) {
                case 1:
                    libros = libroRepository.findByLenguaje("en");
                    break;
                case 2:
                    libros = libroRepository.findByLenguaje("es");
                    break;

                default:
                    System.out.println("Ingresa una opcion valida");
            }

            libros.stream().forEach(l -> {
                System.out.println("""    
                        Titulo: %s
                        Author: %s
                        Lenguaje: %s
                        Descargas: %s
                    """.formatted(l.getTitulo(),
                        l.getAutor(),
                        l.getLenguaje(),
                        l.getDescargas().toString()));
            });

        } catch (Exception e){
            System.out.println("Ingresa un valor valido");
        }
    }
}