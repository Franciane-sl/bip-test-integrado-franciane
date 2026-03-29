package com.example.backend.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficioResponseDto {

    private Long id;          
    private String nome;      
    private String descricao; 
    private BigDecimal valor; 
    private Boolean ativo;   
}