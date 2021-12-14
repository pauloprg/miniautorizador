package br.com.vr.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.controller.form.TransacaoForm;
import br.com.vr.miniautorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.exception.SaldoInsuficienteException;
import br.com.vr.miniautorizador.exception.SenhaInvalidaException;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.repository.CartaoRepository;

@Service
public class TransacoesService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	@Transactional
	public void transacao(TransacaoForm form) {
		Optional<Cartao> cartao = cartaoRepository.findByNumeroCartao(form.getNumeroCartao());
		cartao.orElseThrow(() -> new CartaoInexistenteException());
		
		Optional<Cartao> cartaoSenha = cartaoRepository.findByNumeroCartaoAndSenha(form.getNumeroCartao(), form.getSenhaCartao());
		cartaoSenha.orElseThrow(() -> new SenhaInvalidaException());
		
		Optional<Cartao> cartaoSaldo = cartaoRepository.verificarSaldoDoCartao(form.getNumeroCartao(), form.getValor());
		cartaoSaldo.orElseThrow(() -> new SaldoInsuficienteException());
		
		BigDecimal saldo = cartao.get().getSaldo().subtract(form.getValor());
		cartao.get().setSaldo(saldo);
		cartaoRepository.save(cartao.get());
	}
}
