package edu.ntnu.idatt2105.calculator.service;

import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.calculator.repository.EquationRepository;
import edu.ntnu.idatt2105.calculator.repository.UserRepository;
import edu.ntnu.idatt2105.calculator.dto.ExpressionDTO;
import edu.ntnu.idatt2105.calculator.dto.UserDTO;
import edu.ntnu.idatt2105.calculator.exception.UserNotFoundException;
import edu.ntnu.idatt2105.calculator.mapper.CalculationMapper;
import edu.ntnu.idatt2105.calculator.model.Equation;
import edu.ntnu.idatt2105.calculator.model.User;
import edu.ntnu.idatt2105.calculator.dto.EquationDTO;

import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.ArrayList;

@Service
public class CalculationService {
    private final EquationRepository equationRepository;
    private final CalculationMapper calculationMapper;
    private final Logger logger = LoggerFactory.getLogger(CalculationService.class);
    private final UserRepository userRepository;

    public CalculationService(EquationRepository equationRepository, CalculationMapper calculationMapper, UserRepository userRepository) {
        this.equationRepository = equationRepository;
        this.calculationMapper = calculationMapper;
        this.userRepository = userRepository;
    }

    public double calculate(ExpressionDTO expression) {
        double baseVal = Double.parseDouble(expression.elements().remove(0));
        while(!expression.elements().isEmpty()) {
            String operator = expression.elements().remove(0);
            double nextVal = Double.parseDouble(expression.elements().remove(0));
            switch(operator) {
                case "+":
                    baseVal += nextVal;
                    break;
                case "-":
                    baseVal -= nextVal;
                    break;
                case "*":
                    baseVal *= nextVal;
                    break;
                case "/":
                if (nextVal == 0) {
                    throw new ArithmeticException("Cannot divide by zero.");
                }
                    baseVal /= nextVal;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }
        return baseVal;
    }

    public double saveExpression(ExpressionDTO expression, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        double result = calculate(new ExpressionDTO(new ArrayList<>(expression.elements())));
        @SuppressWarnings("null")
        Equation equation = equationRepository.save(calculationMapper.toEquation(expression, result, user)); 
        System.out.println(equation.getExpression() + " " + equation.getResult());
        logger.info("The expression has been saved");
        return result;
    }

    public List<EquationDTO> getEquations(UserDTO user) {
        return equationRepository.findAllByUser_Username(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException(user.getUsername()))
                .stream().map(CalculationMapper::toEquationDTO).toList();
    }

    public List<ExpressionDTO> getExpressions() {
        List<Equation> equations = equationRepository.findAll();
        return transformEquationListToExpression(equations);
    }

private List<ExpressionDTO> transformEquationListToExpression(List<Equation> equations) {
    return equations
            .stream()
            .map(calculationMapper::toExpression) 
            .toList();
}

}

    