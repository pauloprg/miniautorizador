package br.com.vr.miniautorizador.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vr.miniautorizador.controller.form.TransacaoForm;
import br.com.vr.miniautorizador.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {

	@Autowired
	private TransacaoService transacaoService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<String> transacao(@RequestBody @Validated TransacaoForm form) {
		return transacaoService.transacao(form);
	}
}
