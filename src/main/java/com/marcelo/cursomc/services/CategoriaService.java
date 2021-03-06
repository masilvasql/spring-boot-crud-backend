package com.marcelo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.cursomc.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.repositories.CategoriaRepository;
import com.marcelo.cursomc.services.exceptions.DataIntegrityException;
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
		Categoria newCategoria = find(obj.getId());
		updateCategoria(newCategoria, obj);
		return repo.save(newCategoria);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch (DataIntegrityViolationException ex){
			throw new DataIntegrityException("Não é possível excluir uma categoria que contem produtos!");
		}
	}

	public List<Categoria> findAll(){
		return repo.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.by(Direction.valueOf(direction), orderBy));

		return repo.findAll(pageRequest);
		
	}

	public Categoria fromDTO(CategoriaDTO objDTO){
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}

	private void updateCategoria(Categoria newCategoria, Categoria obj){
		newCategoria.setNome(obj.getNome());
	}

}
