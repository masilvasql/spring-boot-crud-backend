package com.marcelo.cursomc.services;

import com.marcelo.cursomc.domain.Pedido;
import com.marcelo.cursomc.repositories.PedidoRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository pedidoRepository;

    public Pedido buscar(Integer id){
        Optional<Pedido> pedido = pedidoRepository.findById(id);
        return pedido.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado ID " +
                id + ", Tipo : " + Pedido.class.getName()));
    }

}
