package com.softwaresolutions.crudclients.dto.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public List<FieldMessage> getErros() {
        return errors;
    }

    public  void addErrors(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));

    }
}
