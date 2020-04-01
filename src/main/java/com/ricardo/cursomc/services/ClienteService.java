package com.ricardo.cursomc.services;


import com.ricardo.cursomc.domain.Cidade;
import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.Endereco;
import com.ricardo.cursomc.domain.enums.Perfil;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.dto.ClienteDTO;
import com.ricardo.cursomc.dto.ClienteNewDTO;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.repositories.EnderecoRepository;
import com.ricardo.cursomc.security.UserSS;
import com.ricardo.cursomc.services.exceptions.AuthorizationException;
import com.ricardo.cursomc.services.exceptions.DataIntegrityException;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    public Cliente find(Integer id){

        UserSS user = UserService.authenticated();
        if ((user ==null || !user.hasRole(Perfil.ADMIN))&& !id.equals(user.getId())){
          throw new AuthorizationException("Acesso negado");
        }

        Cliente cliente = clienteRepository.findOne(id);
        if (cliente == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Cliente.class.getName());
        }
        return cliente;
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.save(obj.getEnderecos());
        return obj;

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
        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(),  null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO){
        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()) );
        Cidade cid = new Cidade( objDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());
        if (objDTO.getTelefone2() != null){
            cli.getTelefones().add(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3() != null){
            cli.getTelefones().add(objDTO.getTelefone3());
        }

        return cli;
    }
}
