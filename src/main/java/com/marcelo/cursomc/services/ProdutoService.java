package com.marcelo.cursomc.services;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.domain.Produto;
import com.marcelo.cursomc.repositories.CategoriaRepository;
import com.marcelo.cursomc.repositories.ProdutoRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
        Optional<Produto> Produto = produtoRepository.findById(id);
        return Produto.orElseThrow(()-> new ObjectNotFoundException("Produto não encontrado ID " +
                id + ", Tipo : " + Produto.class.getName()));
    }

    public List<Produto> findAll(){
        return produtoRepository.findAll();
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.by(Sort.Direction.valueOf(direction), orderBy));

        List<Categoria> categorias = categoriaRepository.findAllById(ids);

        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

}
