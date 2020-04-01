package com.ricardo.cursomc;

import com.ricardo.cursomc.domain.*;
import com.ricardo.cursomc.domain.enums.EstadoPagamento;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.repositories.*;
import com.ricardo.cursomc.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

	}
}
