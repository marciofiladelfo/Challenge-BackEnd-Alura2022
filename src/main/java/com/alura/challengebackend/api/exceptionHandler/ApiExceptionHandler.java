package com.alura.challengebackend.api.exceptionHandler;

import com.alura.challengebackend.domain.exceptions.EntidadeNaoEncontradaException;
import com.alura.challengebackend.domain.exceptions.NegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<Field> fields = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            fields.add(new Field(nome, mensagem));
        }

        Errors errors = new Errors();
        errors.setStatus(status.value());
        errors.setDataHora(OffsetDateTime.now());
        errors.setTitulo("Um ou mais compos estão inválidos. Faça o preenchimento novamente!");
        errors.setFieldsList(fields);
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        Errors errors = new Errors();
        errors.setStatus(status.value());
        errors.setDataHora(OffsetDateTime.now());
        errors.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        Errors errors = new Errors();
        errors.setStatus(status.value());
        errors.setDataHora(OffsetDateTime.now());
        errors.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public String errorPage() {
//        var lista = Category.values();
//        return "Categoria não existe!\n" + "Inserir apenas as seguintes categorias: "
//                + Arrays.toString(lista);
//    }

}
