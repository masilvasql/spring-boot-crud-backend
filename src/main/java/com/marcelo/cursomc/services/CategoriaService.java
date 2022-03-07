package com.marcelo.cursomc.services;

import java.util.Optional;


import com.marcelo.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.repositories.CategoriaRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired // significa que essa dependência será instanciada pelo Spring
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado! ID: " 
		+ id + ", Tipo: " + Categoria.class.getName() ));
	}

	public Categoria insert(Categoria obj){
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj){
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch (DataIntegrityViolationException ex){
			throw new DataIntegrityException("Não é possível excluir uma categoria que contem produtos!");
		}

	}


}
