package br.com.vr.miniautorizador.controller;

import java.net.URI;
import java.util.List;

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
import br.com.vr.miniautorizador.exception.CartaoExistenteException;
import br.com.vr.miniautorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.service.CartoesService;

@RestController
@RequestMapping("/cartoes")
public class CartoesController {

	@Autowired
	private CartoesService cartaoService;
	
	@GetMapping
	public List<CartaoDto> lista() {
		return cartaoService.listar();
	}
	
	@PostMapping
	public ResponseEntity<CartaoDto> cadastrar(@RequestBody @Validated CartaoForm form, UriComponentsBuilder uriBuilder){
		Cartao cartao = form.converter();
		try {
			cartaoService.cadastrar(cartao); 
			URI uri = uriBuilder.path("/cartoes/{numeroCartao}").buildAndExpand(cartao.getNumeroCartao()).toUri();
			return ResponseEntity.created(uri).body(new CartaoDto(cartao)); 
			
		} catch (CartaoExistenteException e) {
			return ResponseEntity.unprocessableEntity().body(new CartaoDto(cartao));
		}
	}
	
	@GetMapping("/{numeroCartao}")
	public ResponseEntity<String> obterSaldo(@PathVariable String numeroCartao) {
		try {
			return ResponseEntity.ok(cartaoService.obterSaldo(numeroCartao));
			
		} catch (CartaoInexistenteException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
