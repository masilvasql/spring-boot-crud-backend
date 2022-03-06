package com.marcelo.cursomc.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.repositories.CategoriaRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // significa que essa dependência será instanciada pelo Spring
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! ID: " 
		+ id + ", Tipo: " + Categoria.class.getName() ));
	}

}
