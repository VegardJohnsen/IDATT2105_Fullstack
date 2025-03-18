package edu.ntnu.idatt2105.calculator.dto;

import lombok.Builder;

@Builder
public record EquationDTO(String expression, double result) {
}
