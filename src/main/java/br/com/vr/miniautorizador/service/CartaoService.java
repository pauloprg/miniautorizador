package br.com.vr.miniautorizador.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.vr.miniautorizador.controller.dto.CartaoDto;
import br.com.vr.miniautorizador.controller.form.CartaoForm;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.repository.CartaoRepository;

@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	public ResponseEntity<CartaoDto> cadastrar(CartaoForm form, UriComponentsBuilder uriBuilder) {
		Cartao cartao = form.converter();
		Optional<Cartao> cartaoRetorno = cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao());
		
		if (cartaoRetorno.isPresent()) {
			return ResponseEntity.unprocessableEntity().body(new CartaoDto(cartao));
		}
		
		cartaoRepository.save(cartao);
		URI uri = uriBuilder.path("/cartoes/{id}").buildAndExpand(cartao.getId()).toUri();
		return ResponseEntity.created(uri).body(new CartaoDto(cartao));
		
	}

	public List<CartaoDto> listar() {
		List<Cartao> cartoes = cartaoRepository.findAll();	
		return CartaoDto.converter(cartoes);
	}

	public ResponseEntity<String> obterSaldo(String numeroCartao) {
		Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
		
		if (cartao.isPresent()) {
			return ResponseEntity.ok(cartao.get().getSaldo().toString());
		}
		
		return ResponseEntity.notFound().build();
	}

}
