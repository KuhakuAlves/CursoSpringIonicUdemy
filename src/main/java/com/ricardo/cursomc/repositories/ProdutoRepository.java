package com.ricardo.cursomc.repositories;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Produto obj  INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome%  AND cat in :categorias ")
    Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

}
