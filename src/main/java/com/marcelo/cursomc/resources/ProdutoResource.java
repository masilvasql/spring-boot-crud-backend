package com.marcelo.cursomc.resources;

import com.marcelo.cursomc.domain.Categoria;
import com.marcelo.cursomc.domain.Produto;
import com.marcelo.cursomc.dto.CategoriaDTO;
import com.marcelo.cursomc.dto.ProdutoDTO;
import com.marcelo.cursomc.resources.utils.URL;
import com.marcelo.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "produtos")
public class ProdutoResource {

    @Autowired
    ProdutoService produtoService;

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id){
        Produto Produto = produtoService.find(id);
        return ResponseEntity.ok(Produto);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProdutoDTO>> findAll(){
        List<Produto> produtos = produtoService.findAll();
        List<ProdutoDTO> produtoDTOS = produtos.stream().map(ProdutoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(produtoDTOS);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ProdutoDTO>> findPage
            (
                    @RequestParam(value="nome", defaultValue = "") String nome,
                    @RequestParam(value="categorias", defaultValue = "") String categorias,
                    @RequestParam(value="page", defaultValue = "0") Integer page,
                    @RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage,
                    @RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
                    @RequestParam(value="direction", defaultValue = "ASC") String direction
            )
    {
        List<Integer> categoriasInteger = URL.decodeIntList(categorias);
        String nomeDecoded = URL.decodeParam(nome);
        Page<Produto> paginacao = produtoService.search(nomeDecoded, categoriasInteger, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDto = paginacao.map(ProdutoDTO::new);
        return ResponseEntity.ok().body(listDto);
    }

}
