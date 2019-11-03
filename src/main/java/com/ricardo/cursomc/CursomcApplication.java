package com.ricardo.cursomc;

import com.ricardo.cursomc.domain.*;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2.000 );
		Produto p2 = new Produto(null, "Impressora", 800.00 );
		Produto p3 = new Produto(null, "Mouse", 80.00 );

		cat1.getProdutos().addAll(Arrays.asList(p1, p2 ,p3));
		cat2.getProdutos().addAll(Arrays.asList( p2 ));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.save(Arrays.asList(cat1, cat2));
		produtoRepository.save(Arrays.asList(p1, p2, p3));

		Estado estado = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", estado);
		Cidade c2 = new Cidade(null, "São Paulo", estado2);
		Cidade c3 = new Cidade(null, "Campinas", estado2);

		estado.getCidades().addAll(Arrays.asList(c1));
		estado2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.save(Arrays.asList(estado, estado2));
		cidadeRepository.save(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36978912377", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(Arrays.asList(cli1));

		enderecoRepository.save(Arrays.asList(e1, e2));

	}
}
