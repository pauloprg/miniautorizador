package br.com.vr.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.vr.miniautorizador.controller.form.TransacaoForm;
import br.com.vr.miniautorizador.modelo.Cartao;
import br.com.vr.miniautorizador.modelo.MensagemErro;
import br.com.vr.miniautorizador.repository.CartaoRepository;

@Service
public class TransacaoService {

	@Autowired
	private CartaoRepository cartaoRepository;
	
	public ResponseEntity<String> transacao(TransacaoForm form) {
		Optional<Cartao> cartaoExistente = cartaoRepository.findByNumeroCartao(form.getNumeroCartao());
		
		if (cartaoExistente.isPresent()) {
			if (cartaoExistente.get().getSenha().equals(form.getSenhaCartao())) {
				
				BigDecimal saldo = cartaoExistente.get().getSaldo().subtract(form.getValor());
				
				if(saldo.compareTo(BigDecimal.ZERO) >= 0 ) {
					cartaoExistente.get().setSaldo(saldo);
					cartaoRepository.save(cartaoExistente.get());
					return ResponseEntity.status(HttpStatus.CREATED).body("OK");
				}
				
				return ResponseEntity.unprocessableEntity().body(MensagemErro.SALDO_INSUFICIENTE.toString());
			}
			
			return ResponseEntity.unprocessableEntity().body(MensagemErro.SENHA_INVALIDA.toString());
		}
		
		return ResponseEntity.unprocessableEntity().body(MensagemErro.CARTAO_INEXISTENTE.toString());
	}

}
