package lk.ijse.dep9.app.advice;

import lk.ijse.dep9.app.exceptions.AccessDeniedException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> validateExceptions(MethodArgumentNotValidException e){
       Map<String, Object> errAttributes=new LinkedHashMap<>();
       errAttributes.put("status",HttpStatus.BAD_REQUEST.value());
       errAttributes.put("error",HttpStatus.BAD_REQUEST.getReasonPhrase());
       errAttributes.put("timestamp",new Date().toString());
        List<String> validationErrorList = e.getFieldErrors().stream().map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList());
        errAttributes.put("validation errors",validationErrorList);
       return errAttributes;
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public Map<String, Object> duplicateEntityExceptionHandler(){
        Map<String, Object> errAttributes=new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.CONFLICT.value());
        errAttributes.put("error",HttpStatus.CONFLICT.getReasonPhrase());
        errAttributes.put("message","Duplicate Entry Found");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;
    }
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, Object> AccessDeniedExceptionHandler(){
        Map<String, Object> errAttributes=new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.FORBIDDEN.value());
        errAttributes.put("error",HttpStatus.FORBIDDEN.getReasonPhrase());
        errAttributes.put("message","Access Denied");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String, Object> emptyResultDataAccessExceptionHandler(){
        Map<String, Object> errAttributes=new LinkedHashMap<>();
        errAttributes.put("status",HttpStatus.NOT_FOUND.value());
        errAttributes.put("error",HttpStatus.NOT_FOUND.getReasonPhrase());
        errAttributes.put("message","Not Found");
        errAttributes.put("timestamp",new Date().toString());
        return errAttributes;
    }

}
