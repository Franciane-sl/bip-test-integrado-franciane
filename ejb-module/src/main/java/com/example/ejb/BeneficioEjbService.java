package com.example.ejb;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.math.BigDecimal;

import com.example.domain.model.entity.Beneficio;

@Stateless
public class BeneficioEjbService {

    @PersistenceContext
    private EntityManager em;

    public void transfer(Long fromId, Long toId, BigDecimal amount) {

       
        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Não foi possivel transferir.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }

      
        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.OPTIMISTIC);
        Beneficio to   = em.find(Beneficio.class, toId, LockModeType.OPTIMISTIC);

      
        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        
        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência.");
        }

       
        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

       
        em.merge(from);
        em.merge(to);
    }
}