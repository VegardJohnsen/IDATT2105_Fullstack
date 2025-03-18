package edu.ntnu.idatt2105.calculator.controller;

import edu.ntnu.idatt2105.calculator.service.CalculationService;
import edu.ntnu.idatt2105.calculator.dto.ExpressionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/calculator")
@CrossOrigin(origins = "*") // This is enough for the whole controller, no need to repeat on each method
public class CalculationController {
    private final CalculationService calculatorService;
    private static final Logger logger = LoggerFactory.getLogger(CalculationController.class);

    public CalculationController(CalculationService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/calculate") // Changed from GetMapping to PostMapping
    public ResponseEntity<Double> calculate(@RequestBody ExpressionDTO expression, Authentication authentication) {
        try {
            double result = calculatorService.saveExpression(expression, authentication.getName());
            logger.info("The expression {} for user {} yielded: {}", expression, authentication.getName(), result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.error("Failed to calculate the expression: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }
}
