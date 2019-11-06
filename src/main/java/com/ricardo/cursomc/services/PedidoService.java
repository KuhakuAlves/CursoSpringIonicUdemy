package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.Pedido;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.repositories.PedidoRepository;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscar(Integer id){
        Pedido pedido = pedidoRepository.findOne(id);
        if (pedido == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Pedido.class.getName());
        }
        return pedido;
    }
}
