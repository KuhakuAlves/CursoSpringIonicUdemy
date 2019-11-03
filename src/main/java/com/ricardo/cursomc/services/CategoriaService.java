package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.repositories.CategoriaRepository;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria buscar(Integer id){
        Categoria categoria = categoriaRepository.findOne(id);
        if (categoria == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Categoria.class.getName());
        }
        return categoria;
    }
}
