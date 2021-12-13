package br.com.vr.miniautorizador.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.vr.miniautorizador.modelo.Cartao;

public class CartaoDto {
	
	private String senha;
	private String numeroCartao;
	
	
	public CartaoDto(Cartao cartao) {
		this.numeroCartao = cartao.getNumeroCartao();
		this.senha = cartao.getSenha();
	}
	
	public String getNumeroCartao() {
		return numeroCartao;
	}
	public String getSenha() {
		return senha;
	}

	public static List<CartaoDto> converter(List<Cartao> cartoes) {
		return cartoes.stream().map(CartaoDto::new).collect(Collectors.toList());
	}
}
