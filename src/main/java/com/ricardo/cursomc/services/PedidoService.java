package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.ItemPedido;
import com.ricardo.cursomc.domain.PagamentoComBoleto;
import com.ricardo.cursomc.domain.Pedido;
import com.ricardo.cursomc.domain.enums.EstadoPagamento;
import com.ricardo.cursomc.repositories.*;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id){
        Pedido pedido = pedidoRepository.findOne(id);
        if (pedido == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Pedido.class.getName());
        }
        return pedido;
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstance(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDETE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagtro = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagtro, obj.getInstance());
        }
        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()){
           ip.setDesconto(0.0);
           ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());
           ip.setPedido(obj);
        }
        itemPedidoRepository.save(obj.getItens());
        return obj;
    }
}
