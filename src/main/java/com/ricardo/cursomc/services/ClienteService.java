package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.dto.CategoriaDTO;
import com.ricardo.cursomc.dto.ClienteDTO;
import com.ricardo.cursomc.repositories.CategoriaRepository;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.services.exceptions.DataIntegrityException;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente find(Integer id){
        Cliente cliente = clienteRepository.findOne(id);
        if (cliente == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Cliente.class.getName());
        }
        return cliente;
    }

    public Cliente update(Cliente obj) {
        Cliente cliente = find(obj.getId());
        updateData(cliente, obj);
        return clienteRepository.save(cliente);
    }

    private void updateData(Cliente cliente, Cliente obj) {
        cliente.setNome(obj.getNome());
        cliente.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {

            clienteRepository.delete(id);
        }catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possivel excluir por que ha entidades relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO){
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),  null, null);
    }
}
