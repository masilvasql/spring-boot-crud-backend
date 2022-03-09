package com.marcelo.cursomc.resources;

import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.services.ClienteService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="clientes")
public class ClienteResource {

	@Autowired 
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		Cliente cliente = service.find(id);
		return ResponseEntity.ok(cliente);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(ClienteDTO::new).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage
			(
					@RequestParam(value="page", defaultValue = "0") Integer page,
					@RequestParam(value="linesPerPage", defaultValue = "24")Integer linesPerPage,
					@RequestParam(value="orderBy", defaultValue = "nome")String orderBy,
					@RequestParam(value="direction", defaultValue = "ASC") String direction
			)
	{
		Page<Cliente> paginacao = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = paginacao.map(ClienteDTO::new);
		return ResponseEntity.ok().body(listDto);
	}
	
	
	
}
