package com.ricardo.cursomc.repositories;

import com.ricardo.cursomc.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {

}
