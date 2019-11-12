package com.ricardo.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    private static final long serialVersionUID = 1L;

    private List<FieldMessagem> error = new ArrayList<>();
    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FieldMessagem> getErrors() {
        return error;
    }

    public void addError(String fieldName, String message) {
        this.error.add(new FieldMessagem(fieldName, message));
    }
}
