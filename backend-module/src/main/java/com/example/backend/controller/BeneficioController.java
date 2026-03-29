package com.example.backend.controller;

import com.example.backend.model.dto.BeneficioRequestDto;
import com.example.backend.model.dto.BeneficioResponseDto;
import com.example.backend.service.BeneficioService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
@RequiredArgsConstructor
public class BeneficioController {

    private final BeneficioService beneficioService;

 
    @GetMapping
    public ResponseEntity<List<BeneficioResponseDto>> listAll() {
        List<BeneficioResponseDto> beneficios = beneficioService.listAll();
        return ResponseEntity.ok(beneficios);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BeneficioResponseDto> getById(@PathVariable Long id) {
        return beneficioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    
    @PostMapping
    public ResponseEntity<BeneficioResponseDto> create(@RequestBody BeneficioRequestDto dto) {
        BeneficioResponseDto saved = beneficioService.save(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<BeneficioResponseDto> update(
            @PathVariable Long id,
            @RequestBody BeneficioRequestDto dto) {
        BeneficioResponseDto updated = beneficioService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        beneficioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam BigDecimal amount) {
        beneficioService.transfer(fromId, toId, amount);
        return ResponseEntity.ok().build();
    }
}