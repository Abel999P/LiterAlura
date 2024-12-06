package com.alura.literalura;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.LibroRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner
{

	public static void main(String[] args)
	{
		SpringApplication.run(LiteraluraApplication.class,args);
	}

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AuthorRepository authorRepository;
	public void run(String[] args) {
		Principal principal = new Principal(repository, authorRepository);
		principal.showMenu();
	}
}