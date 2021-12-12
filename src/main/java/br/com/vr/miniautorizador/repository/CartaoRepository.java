package br.com.vr.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.vr.miniautorizador.Modelo.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long>{

}
