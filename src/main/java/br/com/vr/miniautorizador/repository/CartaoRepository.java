package br.com.vr.miniautorizador.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.vr.miniautorizador.modelo.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{
	Optional<Cartao> findByNumeroCartao(String numeroCartao);

	Optional<Cartao> findByNumeroCartaoAndSenha(String numeroCartao, String senhaCartao);
	
	@Query(value = "select * from cartao ca"
			+ " where ca.numero_Cartao = ?1 and ca.saldo >= ?2"
			+ " ", nativeQuery = true)
	Optional<Cartao> verificarSaldoDoCartao(String numeroCartao, BigDecimal valor);
}
