package br.com.vr.miniautorizador.controller.form;

import br.com.vr.miniautorizador.Modelo.Cartao;

public class CartaoForm {

	private String numeroCartao;
	private String senha;
	
	public String getNumeroCartao() {
		return numeroCartao;
	}
	public void setNumeroCartao(String numeroCartao) {
		this.numeroCartao = numeroCartao;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Cartao converter() {
		Cartao cartao = new Cartao();
		cartao.setNumeroCartao(this.numeroCartao);
		cartao.setSenha(this.senha);
		
		return cartao;
	}
	
}
