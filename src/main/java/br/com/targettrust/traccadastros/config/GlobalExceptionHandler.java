package br.com.targettrust.traccadastros.config;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	
	@Autowired
	private transient Logger logger;
	
	@ExceptionHandler({NotFoundRuntimeException.class})
	public ResponseEntity handlerException(Throwable ex) {
		logger.error("handlerException",ex);
		return ResponseEntity.notFound().build();
	}
	
	
	@ExceptionHandler(VeiculoIndisponivelRuntimeException.class)
	public ResponseEntity handlerVeiculoIndisponivel(VeiculoIndisponivelRuntimeException ex) {
		return ResponseEntity.badRequest().body(ex.getErro());
	}
	
	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErroModel handlerConstantViolation(ConstraintViolationException ex) {
		return new ErroModel("102", ex.getConstraintViolations().toString());
	}
	

}
