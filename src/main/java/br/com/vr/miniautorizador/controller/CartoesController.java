package br.com.vr.miniautorizador.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vr.miniautorizador.Modelo.Cartao;
import br.com.vr.miniautorizador.controller.dto.CartaoDto;
import br.com.vr.miniautorizador.controller.form.CartaoForm;
import br.com.vr.miniautorizador.repository.CartaoRepository;

@RestController
@RequestMapping("/cartoes")
public class CartoesController {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	
	@GetMapping
	public List<CartaoDto> lista() {
		List<Cartao> cartoes = cartaoRepository.findAll();
		return CartaoDto.converter(cartoes);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CartaoDto> cadastrar(@RequestBody @Validated CartaoForm form, UriComponentsBuilder uriBuilder) {
		Cartao cartao = form.converter();
		cartaoRepository.save(cartao);
		
		URI uri = uriBuilder.path("/cartoes/{id}").buildAndExpand(cartao.getId()).toUri();
		return ResponseEntity.created(uri).body(new CartaoDto(cartao));
	}
}
