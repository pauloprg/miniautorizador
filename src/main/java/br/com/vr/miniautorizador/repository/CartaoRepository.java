package br.com.vr.miniautorizador.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.miniautorizador.modelo.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

	Optional<Cartao> findByNumeroCartao(String numeroCartao);

}
