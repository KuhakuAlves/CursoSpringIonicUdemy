package com.ricardo.cursomc.services;

import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.ItemPedido;
import com.ricardo.cursomc.domain.PagamentoComBoleto;
import com.ricardo.cursomc.domain.Pedido;
import com.ricardo.cursomc.domain.enums.EstadoPagamento;
import com.ricardo.cursomc.repositories.*;
import com.ricardo.cursomc.security.UserSS;
import com.ricardo.cursomc.services.exceptions.AuthorizationException;
import com.ricardo.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    public Pedido find(Integer id){
        Pedido pedido = pedidoRepository.findOne(id);
        if (pedido == null){
          throw new ObjectNotFoundException("Objeto não encontrado id: " + id + "Tipo: " + Pedido.class.getName());
        }
        return pedido;
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDETE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagtro = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagtro, obj.getInstante());
        }
        obj = pedidoRepository.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()){
           ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
           ip.setDesconto(0.0);
           ip.setPreco(ip.getProduto().getPreco());
           ip.setPedido(obj);
        }
        itemPedidoRepository.save(obj.getItens());
        emailService.sendOrderConfirmationHtmlEmail(obj);
        return obj;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Acesso negado");
        }
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteRepository.findOne(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);
    }
}
