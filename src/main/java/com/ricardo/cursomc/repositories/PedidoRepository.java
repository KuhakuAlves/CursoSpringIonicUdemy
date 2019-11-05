package com.ricardo.cursomc.repositories;

import com.ricardo.cursomc.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
