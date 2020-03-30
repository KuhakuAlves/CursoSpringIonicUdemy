package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.domain.Pedido;
import com.ricardo.cursomc.domain.Produto;
import com.ricardo.cursomc.repositories.CategoriaRepository;
import com.ricardo.cursomc.repositories.ProdutoRepository;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto find(Integer id){
        Produto pproduto = produtoRepository.findOne(id);
        if (pproduto == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Pedido.class.getName());
        }
        return pproduto;
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAll(ids);
        return produtoRepository.search(nome, categorias, pageRequest);
    }
}
