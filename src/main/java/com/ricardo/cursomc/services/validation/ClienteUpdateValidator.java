package com.ricardo.cursomc.services.validation;

import com.ricardo.cursomc.domain.Cliente;
import com.ricardo.cursomc.domain.enums.TipoCliente;
import com.ricardo.cursomc.dto.ClienteDTO;
import com.ricardo.cursomc.dto.ClienteNewDTO;
import com.ricardo.cursomc.repositories.ClienteRepository;
import com.ricardo.cursomc.resources.exceptions.FieldMessagem;
import com.ricardo.cursomc.services.validation.utils.BR;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO>{

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {

    }
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        Map<String, String> map = (Map<String, String>)httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessagem> list = new ArrayList<>();

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null && !aux.getId().equals(uriId)){
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
