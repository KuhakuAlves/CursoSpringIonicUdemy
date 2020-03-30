package com.ricardo.cursomc.services.validation;

import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.dto.ClienteNewDTO;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.resources.exceptions.FieldMessagem;
import com.ricardo.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO>{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {

    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessagem> list = new ArrayList<>();

        if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())){
            list.add(new FieldMessagem("cpfOuCnpj", "Cpf inválido"));
        }else if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())){
            list.add(new FieldMessagem("cpfOuCnpj", "CNPJ inválido"));
        }

        if(clienteRepository.findByEmail(objDto.getEmail()) != null){
            list.add(new FieldMessagem("email", "Email ja existente"));
        }

        // inclua os testes aqui, inserindo erros na lista

        for (FieldMessagem e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
