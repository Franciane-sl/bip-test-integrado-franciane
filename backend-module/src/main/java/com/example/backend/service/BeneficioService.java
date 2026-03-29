package com.example.backend.service;

import com.example.backend.model.dto.BeneficioRequestDto;
import com.example.backend.model.dto.BeneficioResponseDto;
import com.example.backend.repository.BeneficioRepository;
import com.example.domain.model.entity.Beneficio;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeneficioService {

    private final BeneficioRepository beneficioRepository;

    @Transactional(readOnly = true)
    public List<BeneficioResponseDto> listAll() {
        return beneficioRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<BeneficioResponseDto> getById(Long id) {
        return beneficioRepository.findById(id)
                .map(this::toResponseDto);
    }

    @Transactional
    public BeneficioResponseDto save(BeneficioRequestDto dto) {
        Beneficio beneficio = new Beneficio();
        beneficio.setNome(dto.getNome());
        beneficio.setDescricao(dto.getDescricao());
        beneficio.setValor(dto.getValor());
        beneficio.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        Beneficio saved = beneficioRepository.save(beneficio);
        return toResponseDto(saved);
    }

    @Transactional
    public BeneficioResponseDto update(Long id, BeneficioRequestDto dto) {
        Beneficio beneficio = beneficioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado"));

        beneficio.setNome(dto.getNome());
        beneficio.setDescricao(dto.getDescricao());
        beneficio.setValor(dto.getValor());
        beneficio.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

        Beneficio updated = beneficioRepository.save(beneficio);
        return toResponseDto(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!beneficioRepository.existsById(id)) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }
        beneficioRepository.deleteById(id);
    }

    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Não é possível transferir para o mesmo benefício.");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor deve ser maior que zero.");
        }

        Beneficio from = beneficioRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Benefício de origem não encontrado"));
        Beneficio to = beneficioRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Benefício de destino não encontrado"));

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para transferência.");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        beneficioRepository.save(from);
        beneficioRepository.save(to);
    }

    private BeneficioResponseDto toResponseDto(Beneficio beneficio) {
        return new BeneficioResponseDto(
                beneficio.getId(),
                beneficio.getNome(),
                beneficio.getDescricao(),
                beneficio.getValor(),
                beneficio.getAtivo()
        );
    }
}