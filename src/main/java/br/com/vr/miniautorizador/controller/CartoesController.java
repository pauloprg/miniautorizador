package br.com.vr.miniautorizador.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vr.miniautorizador.controller.dto.CartaoDto;
import br.com.vr.miniautorizador.controller.form.CartaoForm;
import br.com.vr.miniautorizador.service.CartaoService;

@RestController
@RequestMapping("/cartoes")
public class CartoesController {

	@Autowired
	private CartaoService cartaoService;
	
	
	@GetMapping
	public List<CartaoDto> lista() {
		return cartaoService.listar();
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CartaoDto> cadastrar(@RequestBody @Validated CartaoForm form, UriComponentsBuilder uriBuilder) {
		return cartaoService.cadastrar(form, uriBuilder);
	}
	
	@GetMapping("/{numeroCartao}")
	public ResponseEntity<String> obterSaldo(@PathVariable String numeroCartao) {
		return cartaoService.obterSaldo(numeroCartao);
	}
}
