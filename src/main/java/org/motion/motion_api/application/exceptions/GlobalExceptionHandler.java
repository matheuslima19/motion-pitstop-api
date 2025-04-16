package org.motion.motion_api.application.exceptions;

import jakarta.mail.MessagingException;
import org.apache.commons.lang3.NotImplementedException;
import org.motion.motion_api.application.exceptions.util.ErrorHelper;
import org.motion.motion_api.application.exceptions.util.ErrorResponse;
import org.motion.motion_api.application.exceptions.util.PathInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private PathInterceptor pathInterceptor;


    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DadoUnicoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleDadoUnicoDuplicadoException(DadoUnicoDuplicadoException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace(),5));
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleSenhaIncorretaException(InvalidCredentialsException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> handleSQLException(SQLException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SenhaNulaOuVaziaException.class)
    public ResponseEntity<ErrorResponse> handleSenhaNulaOuVaziaException(SenhaNulaOuVaziaException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorResponse> handleNotImplementedException(NotImplementedException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_IMPLEMENTED, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN,ex.getMessage(),path,ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ErrorResponse> handleMessagingException(MessagingException ex){
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,"Could not send email\n "+ex.getMessage(),path,ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        System.out.println(ex.getClass().getTypeName());
        String path = pathInterceptor.getPath();
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), path, ErrorHelper.getStackTracePersonalizado(ex.getStackTrace()));
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}