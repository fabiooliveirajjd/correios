package br.com.fabio.correios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value= HttpStatus.SERVICE_UNAVAILABLE, reason="Serviço está em instalação, por favor aguarde!")//503
public class NotReadyException extends RuntimeException {
}