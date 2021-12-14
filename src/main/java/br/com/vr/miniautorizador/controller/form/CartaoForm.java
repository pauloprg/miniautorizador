package br.com.vr.miniautorizador.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.vr.miniautorizador.modelo.Cartao;

public class CartaoForm {

	@NotNull @NotEmpty
	private String numeroCartao;
	
	@NotNull @NotEmpty
	private String senha;
	
	public CartaoForm() {
		
	}
	
	public CartaoForm(String numeroCartao, String senha) {
		this.numeroCartao = numeroCartao;
		this.senha = senha;
	}
	
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
