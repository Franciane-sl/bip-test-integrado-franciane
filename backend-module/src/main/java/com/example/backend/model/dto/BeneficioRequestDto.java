package com.example.backend.model.dto;

import lombok.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeneficioRequestDto {

    @NotBlank(message = "O nome do benefício é obrigatório")
    @Size(max = 100, message = "O nome do benefício deve ter no máximo 100 caracteres")
    private String nome;
    @Size(max = 255, message = "A descrição do benefício deve ter no máximo 255 caracteres")
    private String descricao;
    @NotNull(message = "O valor do benefício é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor do benefício deve ser um número positivo")
    private BigDecimal valor;
    private Boolean ativo;
}
