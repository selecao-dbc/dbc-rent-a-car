package br.com.targettrust.traccadastros.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private transient Logger logger;
	
	@ExceptionHandler({NotFoundRuntimeException.class})
	public ResponseEntity handlerException(Throwable ex) {
		logger.error("handlerException",ex);
		return ResponseEntity.notFound().build();
	}
	
}
