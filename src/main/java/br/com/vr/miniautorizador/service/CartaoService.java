package br.com.vr.miniautorizador.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.controller.dto.CartaoDto;
import br.com.vr.miniautorizador.exception.CartaoExistenteException;
import br.com.vr.miniautorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.repository.CartaoRepository;


@Service
public class CartaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Transactional
	public void cadastrar(Cartao cartao) {
		Optional<Cartao> cartaoOptional = cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao());
		cartaoOptional.ifPresentOrElse((value) -> {throw new CartaoExistenteException();}, () -> cartaoRepository.save(cartao));
	}

	public List<CartaoDto> listar() {
		List<Cartao> cartoes = cartaoRepository.findAll();	
		return CartaoDto.converter(cartoes);
	}

	public String obterSaldo(String numeroCartao) {
		Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
		return cartao.orElseThrow(() -> new CartaoInexistenteException()).getSaldo().toString();
	}

}
