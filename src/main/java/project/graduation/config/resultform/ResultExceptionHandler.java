package project.graduation.config.resultform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ResultExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResultException.class)
    public ResponseEntity<ResultResponse<Map<String, Object>>> resultResponseErrorHandler(ResultException e) {
        log.error("[exceptionHandle] ex", e);
        ResultResponse<Map<String, Object>> resultResponse = new ResultResponse<>(e.getStatus());
        return new ResponseEntity<>(resultResponse, HttpStatus.valueOf(e.getStatus().getCode()));
    }
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResultResponse<Map<String, Object>> loginErrorHandler(AuthenticationException e) {
        log.error("[exceptionHandle] ex", e);
        return new ResultResponse<>(ResultResponseStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResultResponse<Map<String, Object>> badRequestHandler(HttpMessageNotReadableException e) {
        log.error("[exceptionHandle] ex", e);
        return new ResultResponse<>(ResultResponseStatus.REQUEST_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultResponse<Map<String, Object>> allErrorHandler(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new ResultResponse<>(ResultResponseStatus.SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ResultResponse<Map<String, Object>> allThrowableErrorHandler(Throwable e) {
        log.error("[exceptionHandle] ex", e);
        return new ResultResponse<>(ResultResponseStatus.SERVER_ERROR);
    }
}
