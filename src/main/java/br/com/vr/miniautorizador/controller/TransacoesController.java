package br.com.vr.miniautorizador.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.controller.form.TransacaoForm;
import br.com.vr.miniautorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.exception.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exception.SenhaInvalidaException;
import br.com.vr.miniautorizador.service.TransacoesService;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {

	@Autowired
	private TransacoesService transacaoService;
	
	@PostMapping
	public ResponseEntity<String> transacao(@RequestBody @Validated TransacaoForm form) {
		try {
			transacaoService.transacao(form);
			return ResponseEntity.status(HttpStatus.CREATED).body("OK");
		} catch (CartaoInexistenteException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		} catch (SenhaInvalidaException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		} catch (SaldoInsuficienteException e) {
			return ResponseEntity.unprocessableEntity().body(e.getMessage());
		}
	}
}
