package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.repositories.CategoriaRepository;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente buscar(Integer id){
        Cliente cliente = clienteRepository.findOne(id);
        if (cliente == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Cliente.class.getName());
        }
        return cliente;
    }
}
