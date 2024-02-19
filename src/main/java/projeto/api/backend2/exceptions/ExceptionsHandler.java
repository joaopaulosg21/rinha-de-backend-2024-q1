package projeto.api.backend2.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> notFoundExceptionHandler(RuntimeException exc, WebRequest request) {
        Map<String,String> body = new HashMap<>();
        body.put("message",exc.getMessage());
        return handleExceptionInternal(exc, body, new HttpHeaders(), HttpStatusCode.valueOf(404), request);
    }

    @ExceptionHandler({UnprocessableEntity.class})
    public ResponseEntity<Object> unprocessableEntityExceptionHandler(RuntimeException exc, WebRequest request) {
        Map<String,String> body = new HashMap<>();
        body.put("message",exc.getMessage());
        return handleExceptionInternal(exc, body, new HttpHeaders(), HttpStatusCode.valueOf(422), request);
    }
}
