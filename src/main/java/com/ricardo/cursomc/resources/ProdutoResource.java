package com.ricardo.cursomc.resources;

import com.ricardo.cursomc.domain.Categoria;
import com.ricardo.cursomc.domain.Pedido;
import com.ricardo.cursomc.domain.Produto;
import com.ricardo.cursomc.dto.ProdutoDTO;
import com.ricardo.cursomc.resources.utils.Url;
import com.ricardo.cursomc.services.PedidoService;
import com.ricardo.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {

		Produto produto = produtoService.find(id);
		return ResponseEntity.ok().body(produto);
	}

	@RequestMapping( method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        String nomeDecoded = Url.decodeParam(nome);
		List<Integer> ids = Url.decodeIntList(categorias);
		Page<Produto> list = produtoService.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}

}
