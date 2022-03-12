package com.marcelo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.marcelo.cursomc.domain.Cidade;
import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.domain.Endereco;
import com.marcelo.cursomc.domain.enums.TipoCliente;
import com.marcelo.cursomc.dto.ClienteDTO;
import com.marcelo.cursomc.dto.ClienteNewDTO;
import com.marcelo.cursomc.repositories.CidadeRepository;
import com.marcelo.cursomc.repositories.EnderecoRepository;
import com.marcelo.cursomc.services.exceptions.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Cliente;
import com.marcelo.cursomc.repositories.ClienteRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ID " +
		id + ", Tipo : " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente obj){
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj){
		Cliente newObj =  find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id){
		find(id);
		try{
			repo.deleteById(id);
		}catch (DataIntegrityViolationException ex){
			throw new DataIntegrityException("Não é possível excluir um Cliente porque há entidades relacionadas!");
		}
	}

	public List<Cliente> findAll(){
		return repo.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.by(Sort.Direction.valueOf(direction), orderBy));

		return repo.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO clienteDTO){
		Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null);
		return  cliente;
	}

	public Cliente fromDTO(ClienteNewDTO clienteNewDTO){
		Cliente cliente = new Cliente(
				null,
				clienteNewDTO.getNome(),
				clienteNewDTO.getEmail(),
				clienteNewDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNewDTO.getTipo()));

		Cidade cidade = cidadeRepository.getById(clienteNewDTO.getCidadeId());

		Endereco end = new Endereco(
				null,
				clienteNewDTO.getLogradouro(),
				clienteNewDTO.getNumero(),
				clienteNewDTO.getComplemento(),
				clienteNewDTO.getBairro(),
				clienteNewDTO.getCep(),
				cliente,
				cidade);

		cliente.getEnderecos().add(end);

		cliente.getTelefones().add(clienteNewDTO.getTelefone1());
		if(clienteNewDTO.getTelefone2() != null){
			cliente.getTelefones().add(clienteNewDTO.getTelefone2());
		}

		if(clienteNewDTO.getTelefone3() != null){
			cliente.getTelefones().add(clienteNewDTO.getTelefone3());
		}

		return  cliente;
	}

	private void updateData(Cliente newObj, Cliente obj){
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
